package dev.study.dto.ticketing

data class TicketingCancelDto(
    val movieId: Long,
    val showSeatId: Long,
    val memberId: Long
)
