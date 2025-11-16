package dev.study.ticketing.service

import dev.study.member.repository.MemberRepository
import dev.study.movie.repository.MovieRepository
import dev.study.ticketHistory.repository.TicketHistoryRepository
import org.springframework.stereotype.Service

@Service
class TicketingService(
    private val memberRepository: MemberRepository,
    private val movieRepository: MovieRepository,
    private val ticketHistoryRepository: TicketHistoryRepository,
) {

    fun reserveTicket() {


    }
}