package dev.study.ticketing.service

import dev.study.member.entity.Member
import dev.study.member.repository.MemberRepository
import dev.study.seat.repository.SeatRepository
import dev.study.ticketHistory.repository.TicketHistoryRepository
import dev.study.ticketing.exception.MemberNotFoundException
import dev.study.ticketing.exception.MovieNotFoundException
import dev.study.ticketing.exception.NotEnoughAmountException
import dev.study.ticketing.exception.TicketingNotStartedException
import dev.study.transaction_history.repository.TransactionHistoryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class TicketingService(
    private val memberRepository: MemberRepository,
    private val ticketHistoryRepository: TicketHistoryRepository,
    private val seatRepository: SeatRepository,
    private val transactionHistoryRepository: TransactionHistoryRepository
) {
    private val TICKETING_START_TIME: LocalTime = LocalTime.of(14, 0) // 예매 시작 시간은 14시

    @Transactional
    fun reserveTicket(movieId: Long, memberId: Long) {
        if (LocalTime.now().isBefore(TICKETING_START_TIME)) {
            // 14 시 이전 요청 예외 발생
            throw TicketingNotStartedException("티켓 예매는 14시부터 가능합니다.")
        }

        // 사용자 데이터 가져오기
        val member: Member = memberRepository.findById(memberId).orElse(null)
            ?: throw MemberNotFoundException("존재하지 않는 사용자입니다.")

        // 사용자 잔액이 부족할 경우 예외 발생
        if (member.amount < 15000) throw NotEnoughAmountException("잔액이 충분하지 않습니다.")

        //TODO 좌석 정보 가져오기. redis lock 이용


    }

}