package dev.study.campaign.dto

import dev.study.campaign.domain.CampaignStatus
import dev.study.campaign.entity.Campaign
import java.time.LocalDateTime

data class CampaignResponseDto(
    val id: Long,
    val name: String,
    val description: String?,
    val status: CampaignStatus,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val movieId: Long,
    val movieTitle: String,
    val bannerImageUrl: String,
    val targetUrl: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(campaign: Campaign): CampaignResponseDto {
            return CampaignResponseDto(
                id = requireNotNull(campaign.id),
                name = campaign.name,
                description = campaign.description,
                status = campaign.status,
                startDate = campaign.startDate,
                endDate = campaign.endDate,
                movieId = campaign.movieId,
                movieTitle = campaign.movieTitle,
                bannerImageUrl = campaign.bannerImageUrl,
                targetUrl = campaign.targetUrl,
                createdAt = campaign.createdAt,
                updatedAt = campaign.updatedAt
            )
        }
    }
}
