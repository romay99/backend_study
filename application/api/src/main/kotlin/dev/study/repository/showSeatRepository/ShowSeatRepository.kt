package dev.study.repository.showSeatRepository

import dev.study.entity.showSeat.ShowSeat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ShowSeatRepository : JpaRepository<ShowSeat, Long> {

    @Query("""
        SELECT ss 
        FROM ShowSeat ss
        JOIN FETCH ss.movie
        JOIN FETCH ss.seat
        WHERE 1=1
        AND ss.movie.id=:movieId
        AND ss.seat.col=:col
        AND ss.seat.num=:num
        AND ss.seat.screenNumber=:screenNumber
    """)
    fun findShowSeat(movieId: Long, col: String, num: Int, screenNumber: Int): ShowSeat?
}