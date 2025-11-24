package dev.study.advertisement.service

import dev.study.advertisement.dto.BannerDto
import dev.study.advertisement.dto.MultipleBannersResponseDto
import dev.study.advertisement.dto.SingleBannerResponseDto
import dev.study.campaign.domain.CampaignStatus
import dev.study.campaign.repository.CampaignRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdvertisementService(
    private val campaignRepository: CampaignRepository
) {

    @Transactional(readOnly = true)
    fun getSingleBanner(): SingleBannerResponseDto {
        val campaign = campaignRepository.findFirstByStatus(CampaignStatus.ACTIVE)
            ?: return SingleBannerResponseDto(banner = null)

        return SingleBannerResponseDto(banner = BannerDto.from(campaign))
    }

    @Transactional(readOnly = true)
    fun getMultipleBanners(count: Int = 5): MultipleBannersResponseDto {
        val pageable = PageRequest.of(0, count, org.springframework.data.domain.Sort.by("createdAt").ascending())
        val activeCampaigns = campaignRepository.findByStatus(CampaignStatus.ACTIVE, pageable)

        val banners = activeCampaigns.map { BannerDto.from(it) }
        return MultipleBannersResponseDto(banners = banners, count = banners.size)
    }
}
