package dev.study.repository.showSeatRepository

import dev.study.entity.showSeat.ShowSeat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

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

    @Query("""
        SELECT ss
        FROM ShowSeat ss
        JOIN FETCH ss.movie
        JOIN FETCH ss.seat
        WHERE 1=1
        AND ss.movie.id=:movieId
        AND ss.status = 'AVAILABLE'
    """)
    fun findAvailableShowSeats(movieId: Long): List<ShowSeat>

    @Query("""
        DELETE FROM ShowSeat ss
        WHERE ss.movie.time < :nowTime
    """)
    @Modifying
    fun removePastShowSeats(nowTime: LocalDateTime): Int

    @Query("""
        SELECT ss
        FROM ShowSeat ss
        JOIN FETCH ss.movie
        JOIN FETCH ss.seat
        WHERE 1=1
        AND ss.id = :id
    """)
    fun findShowSeatById(id: Long): ShowSeat?
}
