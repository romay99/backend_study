package dev.study.advertisement.service

import dev.study.advertisement.dto.BannerDto
import dev.study.advertisement.dto.MultipleBannersResponseDto
import dev.study.advertisement.dto.SingleBannerResponseDto
import dev.study.campaign.domain.CampaignStatus
import dev.study.campaign.repository.CampaignRepository
import dev.study.impression.event.ImpressionEvent
import dev.study.impression.service.ImpressionPublisher
import java.time.Instant
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdvertisementService(
    private val campaignRepository: CampaignRepository,
    private val impressionPublisher: ImpressionPublisher,
) {

    @Transactional(readOnly = true)
    fun getSingleBanner(): SingleBannerResponseDto {
        val campaign = campaignRepository.findFirstByStatus(CampaignStatus.ACTIVE)
            ?: return SingleBannerResponseDto(banner = null)

        campaign.id?.run { impressionPublisher.publish(ImpressionEvent(this, Instant.now())) }

        return SingleBannerResponseDto(banner = BannerDto.from(campaign))
    }

    @Transactional(readOnly = true)
    fun getMultipleBanners(count: Int = 5): MultipleBannersResponseDto {
        val pageable = PageRequest.of(0, count, org.springframework.data.domain.Sort.by("createdAt").ascending())
        val activeCampaigns = campaignRepository.findByStatus(CampaignStatus.ACTIVE, pageable)

        activeCampaigns.forEach { campaign ->
            campaign.id?.run { impressionPublisher.publish(ImpressionEvent(this, Instant.now())) }
        }

        val banners = activeCampaigns.map { BannerDto.from(it) }
        return MultipleBannersResponseDto(banners = banners, count = banners.size)
    }
}
