package dev.study.dto.seat

import java.time.LocalDateTime

data class SeatLockDTO (
    val movieId : Long,
    val memberId : Long,
    val col: String,
    val num: Int,
    val startTime: LocalDateTime,
    val screenNumber:Int,
)
