package dev.study.repository.seat

import dev.study.entity.seat.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, Long>
