package dev.study.service.ticketing

import dev.study.domain.showSeat.SeatStatus
import dev.study.dto.seat.SeatLockDTO
import dev.study.dto.ticketing.TicketingTryDto
import dev.study.entity.member.Member
import dev.study.entity.showSeat.ShowSeat
import dev.study.entity.ticketHistory.TicketHistory
import dev.study.entity.transactionHistory.TransactionHistory
import dev.study.exception.member.MemberNotFoundException
import dev.study.exception.member.NotEnoughAmountException
import dev.study.exception.seat.SeatAlreadyOccupiedException
import dev.study.exception.seat.SeatNotAvailableException
import dev.study.exception.seat.SeatNotFoundException
import dev.study.logging.logger
import dev.study.repository.member.MemberRepository
import dev.study.repository.showSeatRepository.ShowSeatRepository
import dev.study.repository.ticketHistory.TicketHistoryRepository
import dev.study.repository.transactionHistory.TransactionHistoryRepository
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.util.concurrent.TimeUnit

@Service
class TicketingService(
    private val memberRepository: MemberRepository,
    private val ticketHistoryRepository: TicketHistoryRepository,
    private val transactionHistoryRepository: TransactionHistoryRepository,
    private val redissonClient: RedissonClient,
    private val showSeatRepository: ShowSeatRepository
) {
    companion object {
        private val TICKET_PRICE = 15000
    }
    private val logger = logger()

    /**
     * 좌석 점유하는 메서드
     */
    @Transactional
    fun holdSeat(dto: SeatLockDTO) : ShowSeat? {
        val col = dto.col
        val num = dto.num
        val memberId = dto.memberId
        val screenNumber = dto.screenNumber
        val showSeat : ShowSeat?
        val movieId = dto.movieId

        val lock =
            // lock:seat:${movieId}:${screenNumber}:${col}:${num}
            redissonClient.getLock("lock:seat:$movieId:$screenNumber:$col:$num")

        try {
            val locked = lock.tryLock(3,5, TimeUnit.SECONDS) // Lock 획득 대기시간 3초, 획득 성공시 5초 동안 점유
            if (!locked){
                // Lock 획득 실패시 예외 발생
                throw SeatAlreadyOccupiedException("이미 선택된 좌석입니다.")
            }
            // 좌석 점유중으로 상태 바꾸기
            // 좌석이 존재하지 않다면 예외 발생
            showSeat = showSeatRepository.findShowSeat(
                movieId = dto.movieId,
                col = dto.col,
                num = dto.num,
                screenNumber = dto.screenNumber)
                ?: throw SeatNotFoundException("존재하지 않는 좌석입니다.")

            // 점유 가능한 좌석이 아닐때 예외 발생
            if (showSeat.status != SeatStatus.AVAILABLE){
                throw SeatNotAvailableException("예매가 불가능한 좌석입니다")
            }

            showSeat.status = SeatStatus.RESERVING // 좌석 상태 점유중으로 변경

            // 5분간 TTL 을 이용한 좌석 자동점유를 위한 KEY
            // 이 KEY 가 만료될때 좌석 점유가 풀린다
            // hold:seat:${movieId}:${screenNumber}:${col}:${num}
            val seatKey = "hold:seat:$movieId:$screenNumber:$col:$num"
            val bucket = redissonClient.getBucket<String>(seatKey)
            bucket.set(memberId.toString(), Duration.ofMinutes(5))
            logger.info("hold:seat:$movieId:$screenNumber:$col:$num BY $memberId")
        } finally {
            // 최종적으로 Lock 이 존재한다면 Lock 해제해주기
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
        return showSeat
    }

    /**
     * 좌석 예매하는 메서드
     */
    @Transactional
    fun reserveTicket(dto: TicketingTryDto) {
        val col = dto.col
        val num = dto.num
        val memberId = dto.memberId
        val screenNumber = dto.screenNumber
        val movieId = dto.movieId

        // 사용자 데이터 가져오기
        val member: Member = memberRepository.findById(dto.memberId).orElse(null)
            ?: throw MemberNotFoundException("존재하지 않는 사용자입니다.")

        // 사용자 잔액이 부족할 경우 예외 발생
        if (member.amount < TICKET_PRICE) throw NotEnoughAmountException("잔액이 충분하지 않습니다.")

        val seatKey = "hold:seat:$movieId:$screenNumber:$col:$num"
        val bucket = redissonClient.getBucket<String>(seatKey)
        val value = bucket.get() // memberId (Long)

        // 좌석 점유 정보가 틀리거나, 다른 사용자가 접근한다면 예외 발생
        if(value == null || value != memberId.toString()){
            throw SeatNotFoundException("존재하지 않는 좌석 정보입니다")
        }

        val showSeat = showSeatRepository.findShowSeat(movieId, col, num, screenNumber)
            ?: throw SeatNotFoundException("존재하지 않는 좌석 정보입니다")

        // 좌석이 점유 상태가 아닐때 예외 발생
        if (showSeat.status != SeatStatus.RESERVING){
            throw SeatNotAvailableException("예매할 수 없는 좌석입니다")
        }

        showSeat.status = SeatStatus.RESERVED // 좌석 상태 변경

        member.amount -= TICKET_PRICE // 사용자 계좌에서 돈 차감 , Dirty Checking 으로 Update

        // 티켓팅 내역 저장
        val ticketHistory = TicketHistory(member = member, movie = showSeat.movie)
        ticketHistoryRepository.save(ticketHistory)

        // 돈 거래 내역 저장
        val transactionHistory = TransactionHistory(amount = TICKET_PRICE, member = member)
        transactionHistoryRepository.save(transactionHistory)

        // redisson key 삭제
        bucket.delete()
        logger.info("reserveTicket:$movieId:$screenNumber:$col:$num")
    }
}