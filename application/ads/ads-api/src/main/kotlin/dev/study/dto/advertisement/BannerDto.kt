package dev.study.dto.advertisement

import dev.study.entity.campaign.Campaign

data class BannerDto(
    val campaignId: Long,
    val campaignName: String,
    val movieId: Long,
    val movieTitle: String,
    val bannerImageUrl: String,
    val targetUrl: String?,
) {
    companion object {
        fun from(campaign: Campaign): BannerDto =
            BannerDto(
                campaignId = requireNotNull(campaign.id),
                campaignName = campaign.name,
                movieId = campaign.movieId,
                movieTitle = campaign.movieTitle,
                bannerImageUrl = campaign.bannerImageUrl,
                targetUrl = campaign.targetUrl,
            )
    }
}
