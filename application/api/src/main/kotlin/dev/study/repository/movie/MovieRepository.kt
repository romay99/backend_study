package dev.study.repository.movie

import dev.study.entity.movie.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface MovieRepository : JpaRepository<Movie, Long> {

    @Query("""
    SELECT m 
    FROM Movie m
    WHERE m.time >= :startTime 
      AND m.time <= :endTime
      AND (:name IS NULL OR m.name LIKE CONCAT('%', :name, '%'))
    """)
    fun findMovieSchedules(startTime: LocalDateTime, endTime: LocalDateTime, name: String?): List<Movie>
}
