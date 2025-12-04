package dev.study.dto.exception

import java.time.LocalDateTime

data class CommonErrorResponse(
    val code: String,
    val message: String,
    val status: Int,
    val createAt: LocalDateTime = LocalDateTime.now()
)
