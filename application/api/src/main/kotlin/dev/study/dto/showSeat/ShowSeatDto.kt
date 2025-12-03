package dev.study.dto.showSeat

import dev.study.domain.showSeat.SeatStatus
import dev.study.entity.showSeat.ShowSeat
import java.time.LocalDateTime

data class ShowSeatDto(
    var id: Long?,
    var seatStatus: SeatStatus,
    var createdAt: LocalDateTime?,
    var seatId: Long?,
) {
    companion object {
        fun from(showSeat: ShowSeat): ShowSeatDto {
            return ShowSeatDto(
                id = showSeat.id,
                seatStatus = showSeat.status,
                createdAt = showSeat.createdAt,
                seatId = showSeat.seat.id
            )
        }
    }
}
