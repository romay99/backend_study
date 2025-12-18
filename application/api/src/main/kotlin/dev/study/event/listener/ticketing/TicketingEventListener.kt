package dev.study.event.listener.ticketing

import dev.study.domain.ticketHistory.TicketHistoryType
import dev.study.domain.transactionHistory.TransactionHistoryType
import dev.study.entity.ticketHistory.TicketHistory
import dev.study.entity.transactionHistory.TransactionHistory
import dev.study.event.listener.TicketingCancelEvent
import dev.study.event.listener.TicketingSuccessEvent
import dev.study.logging.logger
import dev.study.repository.member.MemberRepository
import dev.study.repository.movie.MovieRepository
import dev.study.repository.ticketHistory.TicketHistoryRepository
import dev.study.repository.transactionHistory.TransactionHistoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class TicketingEventListener(
    private val ticketHistoryRepository: TicketHistoryRepository,
    private val transactionHistoryRepository: TransactionHistoryRepository,
    private val memberRepository: MemberRepository,
    private val movieRepository: MovieRepository
) {
    companion object {
        private const val TICKET_PRICE = 15000
    }
    private val logger = logger()

    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun ticketingSuccessSaveLog(
        event: TicketingSuccessEvent
    ) {
        // Lazy Loading , Detached Entity 관련문제로 인해
        // movie 와 member 를 repository 에서 새로 select 해온다.
        val movie = movieRepository.findByIdOrNull(event.movieId)
            ?: return
        val member = memberRepository.findByIdOrNull(event.memberId)
            ?: return

        // 티켓팅 내역 저장
        val ticketHistory = TicketHistory(member = member, movie = movie)
        ticketHistoryRepository.save(ticketHistory)

        // 돈 거래 내역 저장
        val transactionHistory = TransactionHistory(amount = -TICKET_PRICE, member = member)
        transactionHistoryRepository.save(transactionHistory)
    }

    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun ticketingCancelSaveLog(
        event: TicketingCancelEvent
    ) {
        // Lazy Loading , Detached Entity 관련문제로 인해
        // movie 와 member 를 repository 에서 새로 select 해온다.
        val movie = movieRepository.findByIdOrNull(event.movieId)
            ?: return
        val member = memberRepository.findByIdOrNull(event.memberId)
            ?: return

        // 티켓팅 취소 내역 저장
        val ticketHistory = TicketHistory(
            member = member,
            movie = movie,
            type = TicketHistoryType.CANCEL
        )
        ticketHistoryRepository.save(ticketHistory)

        // 돈 거래 취소 내역 저장
        val transactionHistory = TransactionHistory(
            amount = TICKET_PRICE,
            member = member,
            type = TransactionHistoryType.CANCEL
        )
        transactionHistoryRepository.save(transactionHistory)
    }
}
