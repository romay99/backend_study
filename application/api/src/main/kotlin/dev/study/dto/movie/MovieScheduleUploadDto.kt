package dev.study.dto.movie

import java.time.LocalDateTime

data class MovieScheduleUploadDto(
    val name: String,
    val time: LocalDateTime,
    val screenNumber: Int
)
