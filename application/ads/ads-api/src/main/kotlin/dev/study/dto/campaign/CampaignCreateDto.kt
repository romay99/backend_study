package dev.study.dto.campaign

import dev.study.domain.campaign.CampaignStatus
import java.time.LocalDateTime

data class CampaignCreateDto(
    val name: String,
    val description: String? = null,
    val status: CampaignStatus = CampaignStatus.SCHEDULED,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val movieId: Long,
    val movieTitle: String,
    val bannerImageUrl: String,
    val targetUrl: String? = null,
)
