package dev.study.service.advertisement

import dev.study.domain.campaign.CampaignStatus
import dev.study.dto.advertisement.BannerDto
import dev.study.dto.advertisement.MultipleBannersResponseDto
import dev.study.dto.advertisement.SingleBannerResponseDto
import dev.study.event.impression.ImpressionEvent
import dev.study.repository.campaign.CampaignRepository
import dev.study.service.impression.ImpressionPublisher
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class AdvertisementService(
    private val campaignRepository: CampaignRepository,
    private val impressionPublisher: ImpressionPublisher,
) {
    @Transactional(readOnly = true)
    fun getSingleBanner(): SingleBannerResponseDto {
        val campaign =
            campaignRepository.findFirstByStatus(CampaignStatus.ACTIVE)
                ?: return SingleBannerResponseDto(banner = null)

        campaign.id?.run {
            impressionPublisher.publish(ImpressionEvent(this, Instant.now()))
        }

        return SingleBannerResponseDto(banner = BannerDto.from(campaign))
    }

    @Transactional(readOnly = true)
    fun getMultipleBanners(count: Int = 5): MultipleBannersResponseDto {
        val pageable =
            PageRequest.of(
                0,
                count,
                org.springframework.data.domain.Sort
                    .by("createdAt")
                    .descending(),
            )
        val activeCampaigns = campaignRepository.findByStatus(CampaignStatus.ACTIVE, pageable)

        activeCampaigns.forEach { campaign ->
            campaign.id?.run { impressionPublisher.publish(ImpressionEvent(this, Instant.now())) }
        }

        val banners = activeCampaigns.map { BannerDto.from(it) }
        return MultipleBannersResponseDto(banners = banners.content, count = banners.size)
    }
}
