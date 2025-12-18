package dev.study.event.listener

data class TicketingSuccessEvent(
    val memberId: Long,
    val movieId: Long,
)
