package dev.study.entity.showSeat

import dev.study.domain.showSeat.SeatStatus
import dev.study.entity.movie.Movie
import dev.study.entity.seat.Seat
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "show_seat")
open class ShowSeat (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    @Enumerated(EnumType.STRING)
    open var status: SeatStatus = SeatStatus.AVAILABLE,

    open var createdAt: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    open var seat: Seat,
    @ManyToOne(fetch = FetchType.LAZY)
    open var movie: Movie
)
