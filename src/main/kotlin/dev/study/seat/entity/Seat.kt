package dev.study.seat.entity

import dev.study.movie.entity.Movie
import dev.study.seat.domain.SeatStatus
import jakarta.persistence.*

@Entity
@Table(name = "seat")
open class Seat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    open var col: String,
    open var num: Int,
    @Enumerated(EnumType.STRING)
    open var status: SeatStatus = SeatStatus.AVAILABLE,

    @ManyToOne(fetch = FetchType.LAZY)
    open var movie: Movie
)