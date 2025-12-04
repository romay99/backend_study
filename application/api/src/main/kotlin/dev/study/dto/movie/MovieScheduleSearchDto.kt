package dev.study.dto.movie

import java.time.LocalDateTime

data class MovieScheduleSearchDto(
    val name: String?,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
