package dev.study.dto.campaign

import dev.study.domain.campaign.CampaignStatus
import java.time.LocalDateTime

data class CampaignUpdateDto(
    val name: String? = null,
    val description: String? = null,
    val status: CampaignStatus? = null,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val movieId: Long? = null,
    val movieTitle: String? = null,
    val bannerImageUrl: String? = null,
    val targetUrl: String? = null
)
