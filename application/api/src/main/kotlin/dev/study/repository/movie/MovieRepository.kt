package dev.study.repository.movie

import dev.study.entity.movie.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository : JpaRepository<Movie, Long> {
}