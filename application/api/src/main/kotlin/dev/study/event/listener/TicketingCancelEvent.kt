package dev.study.event.listener

data class TicketingCancelEvent(
    val memberId: Long,
    val movieId: Long,
)
