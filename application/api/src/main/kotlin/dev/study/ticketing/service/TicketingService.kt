package dev.study.ticketing.service

import dev.study.member.entity.Member
import dev.study.member.repository.MemberRepository
import dev.study.seat.domain.SeatStatus
import dev.study.seat.entity.Seat
import dev.study.seat.repository.SeatRepository
import dev.study.ticketHistory.entity.TicketHistory
import dev.study.ticketHistory.repository.TicketHistoryRepository
import dev.study.ticketing.dto.TicketingTryDto
import dev.study.ticketing.exception.*
import dev.study.transaction_history.entity.TransactionHistory
import dev.study.transaction_history.repository.TransactionHistoryRepository
import jakarta.transaction.Transactional
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import java.time.LocalTime
import java.util.concurrent.TimeUnit

@Service
class TicketingService(
    private val memberRepository: MemberRepository,
    private val ticketHistoryRepository: TicketHistoryRepository,
    private val seatRepository: SeatRepository,
    private val transactionHistoryRepository: TransactionHistoryRepository,
    private val redissonClient: RedissonClient
) {
    private val TICKETING_START_TIME: LocalTime = LocalTime.of(14, 0) // 예매 시작 시간은 14시

    @Transactional
    fun reserveTicket(dto: TicketingTryDto) {
        if (LocalTime.now().isBefore(TICKETING_START_TIME)) {
            // 14 시 이전 요청 예외 발생
            throw TicketingNotStartedException("티켓 예매는 14시부터 가능합니다.")
        }

        // 사용자 데이터 가져오기
        val member: Member = memberRepository.findById(dto.memberId).orElse(null)
            ?: throw MemberNotFoundException("존재하지 않는 사용자입니다.")

        // 사용자 잔액이 부족할 경우 예외 발생
        if (member.amount < 15000) throw NotEnoughAmountException("잔액이 충분하지 않습니다.")

        // 좌석 점유하기. redis lock 이용
        val seat = holdSeat(dto)

        member.amount -= 15000 // 사용자 계좌에서 돈 차감 , Dirty Checking 으로 Update

        // 티켓팅 내역 저장
        val ticketHistory = TicketHistory(seat = seat,member = member, movie = seat.movie)
        ticketHistoryRepository.save(ticketHistory)

        // 돈 거래 내역 저장
        val transactionHistory = TransactionHistory(amount = 15000, member = member)
        transactionHistoryRepository.save(transactionHistory)
    }

    /**
     * 좌석 점유하는 메서드
     */
    private fun holdSeat(dto: TicketingTryDto) : Seat {
        val col = dto.col
        val num = dto.num
        val seat : Seat

        val lock =
            redissonClient.getLock("lock:seat:$col:$num") // lock:sear:a:1 <- a1 번 좌석 lock 획득 시도

        try {
            val locked = lock.tryLock(3,5, TimeUnit.SECONDS) // Lock 획득 대기시간 3초, 획득 성공시 5초 동안 점유
            if (!locked){
                // Lock 획득 실패시 예외 발생
                throw SeatAlreadyOccupiedException("이미 선택된 좌석입니다.")
            }
            // 좌석 점유중으로 상태 바꾸기
            // 좌석이 존재하지 않다면 예외 발생
            seat = seatRepository.findSeatByColAndNum(dto.col, dto.num)
                ?: throw SeatNotFoundException("존재하지 않는 좌석입니다.")

            seat.status = SeatStatus.RESERVING // 좌석 상태 점유중으로 변경

        } finally {
            // 최종적으로 Lock 이 존재한다면 Lock 해제해주기
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
        return seat
    }
}
