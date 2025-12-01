package dev.study.entity.ticketHistory

import dev.study.domain.ticketHistory.TicketHistoryType
import dev.study.entity.member.Member
import dev.study.entity.movie.Movie
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
)
