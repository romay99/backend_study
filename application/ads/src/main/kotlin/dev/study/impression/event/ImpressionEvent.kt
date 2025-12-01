package dev.study.impression.event

import java.time.Instant

data class ImpressionEvent(
    val campaignId: Long,
    val timestamp: Instant,
    val requestId: String? = null,
    val sessionId: String? = null,
    val userAgent: String? = null,
    val referrer: String? = null,
)
