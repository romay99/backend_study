package dev.study.ticketHistory.entity

import dev.study.member.entity.Member
import dev.study.movie.entity.Movie
import dev.study.seat.entity.Seat
import dev.study.ticketHistory.domain.TicketHistoryType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ticket_history")
open class TicketHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    open var createdAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    open var type: TicketHistoryType = TicketHistoryType.RESERVE,

    @ManyToOne(fetch = FetchType.LAZY)
    open var member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    open var movie: Movie,

    @ManyToOne(fetch = FetchType.LAZY)
    open var seat: Seat
)
