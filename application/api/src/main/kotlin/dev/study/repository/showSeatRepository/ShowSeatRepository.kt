package dev.study.repository.showSeatRepository

import dev.study.entity.showSeat.ShowSeat
import org.springframework.data.jpa.repository.JpaRepository

interface ShowSeatRepository : JpaRepository<ShowSeat, Long> {
}