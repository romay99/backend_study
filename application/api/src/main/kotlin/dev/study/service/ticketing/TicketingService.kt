package dev.study.service.ticketing

import dev.study.entity.member.Member
import dev.study.repository.member.MemberRepository
import dev.study.domain.showSeat.SeatStatus
import dev.study.dto.seat.SeatLockDTO
import dev.study.repository.seat.SeatRepository
import dev.study.repository.ticketHistory.TicketHistoryRepository
import dev.study.dto.ticketing.TicketingTryDto
import dev.study.entity.seat.Seat
import dev.study.exception.member.MemberNotFoundException
import dev.study.exception.member.NotEnoughAmountException
import dev.study.exception.seat.SeatAlreadyOccupiedException
import dev.study.repository.transactionHistory.TransactionHistoryRepository
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Service
class TicketingService(
    private val memberRepository: MemberRepository,
    private val ticketHistoryRepository: TicketHistoryRepository,
    private val seatRepository: SeatRepository,
    private val transactionHistoryRepository: TransactionHistoryRepository,
    private val redissonClient: RedissonClient
) {
    companion object {
        private val TICKET_PRICE = 15000
    }

    @Transactional
    fun reserveTicket(dto: TicketingTryDto) {
        // 사용자 데이터 가져오기
        val member: Member = memberRepository.findById(dto.memberId).orElse(null)
            ?: throw MemberNotFoundException("존재하지 않는 사용자입니다.")

        // 사용자 잔액이 부족할 경우 예외 발생
        if (member.amount < TICKET_PRICE) throw NotEnoughAmountException("잔액이 충분하지 않습니다.")

        // 좌석 점유하기. redis lock 이용
//        val seat = holdSeat(dto)

        member.amount -= TICKET_PRICE // 사용자 계좌에서 돈 차감 , Dirty Checking 으로 Update

        // 티켓팅 내역 저장
        val ticketHistory = TicketHistory(seat = seat, member = member, movie = seat.movie)
        ticketHistoryRepository.save(ticketHistory)

        // 돈 거래 내역 저장
        val transactionHistory = TransactionHistory(amount = 15000, member = member)
        transactionHistoryRepository.save(transactionHistory)
    }

    /**
     * 좌석 점유하는 메서드
     */
    fun lockSeat(dto: SeatLockDTO) : Seat? {
        val col = dto.col
        val num = dto.num
        val movie = dto.movieId
        val member = dto.memberId
        val screenNumber = dto.screenNumber
        val seat : Seat

        val lock =
            // lock:seat:${movieId}:${memberId}:${screenNumber}:${col}:${num}
            redissonClient.getLock("lock:seat:$movie:$member:$screenNumber:$col:$num")

        try {
            val locked = lock.tryLock(3,5, TimeUnit.SECONDS) // Lock 획득 대기시간 3초, 획득 성공시 5초 동안 점유
            if (!locked){
                // Lock 획득 실패시 예외 발생
                throw SeatAlreadyOccupiedException("이미 선택된 좌석입니다.")
            }
            // 좌석 점유중으로 상태 바꾸기
            // 좌석이 존재하지 않다면 예외 발생
//            seat = seatRepository.findSeat(col = col, num = num, movieId = movie)
//                ?: throw SeatNotFoundException("존재하지 않는 좌석입니다.")

//            seat.status = SeatStatus.RESERVING // 좌석 상태 점유중으로 변경

        } finally {
            // 최종적으로 Lock 이 존재한다면 Lock 해제해주기
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
        return seat
    }
}