package dev.study.dto.ticketing

data class TicketingTryDto(
    val movieId : Long,
    val memberId : Long,
    val col: String,
    val num: Int
)