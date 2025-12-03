package dev.study.dto.ticketing

import java.time.LocalDateTime

data class TicketingTryDto(
    val movieId: Long,
    val memberId: Long,
    val col: String,
    val num: Int,
    val startTime: LocalDateTime,
    val screenNumber: Int
)
