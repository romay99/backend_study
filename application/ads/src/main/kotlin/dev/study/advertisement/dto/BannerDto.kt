package dev.study.advertisement.dto

import dev.study.campaign.entity.Campaign

data class BannerDto(
    val campaignId: Long,
    val campaignName: String,
    val movieId: Long,
    val movieTitle: String,
    val bannerImageUrl: String,
    val targetUrl: String?
) {
    companion object {
        fun from(campaign: Campaign): BannerDto {
            return BannerDto(
                campaignId = requireNotNull(campaign.id),
                campaignName = campaign.name,
                movieId = campaign.movieId,
                movieTitle = campaign.movieTitle,
                bannerImageUrl = campaign.bannerImageUrl,
                targetUrl = campaign.targetUrl
            )
        }
    }
}
