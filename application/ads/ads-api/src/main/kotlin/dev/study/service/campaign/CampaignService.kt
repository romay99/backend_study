package dev.study.service.campaign

import dev.study.dto.campaign.CampaignCreateDto
import dev.study.dto.campaign.CampaignResponseDto
import dev.study.dto.campaign.CampaignUpdateDto
import dev.study.entity.campaign.Campaign
import dev.study.exception.campaign.CampaignNotFoundException
import dev.study.repository.campaign.CampaignRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CampaignService(
    private val campaignRepository: CampaignRepository,
) {
    @Transactional
    fun createCampaign(dto: CampaignCreateDto): CampaignResponseDto {
        val campaign =
            Campaign(
                name = dto.name,
                description = dto.description,
                status = dto.status,
                startDate = dto.startDate,
                endDate = dto.endDate,
                movieId = dto.movieId,
                movieTitle = dto.movieTitle,
                bannerImageUrl = dto.bannerImageUrl,
                targetUrl = dto.targetUrl,
            )

        val saved = campaignRepository.save(campaign)
        return CampaignResponseDto.from(saved)
    }

    @Transactional(readOnly = true)
    fun getCampaign(id: Long): CampaignResponseDto {
        val campaign =
            campaignRepository.findById(id).orElseThrow {
                CampaignNotFoundException("캠페인을 찾을 수 없습니다. ID: $id")
            }
        return CampaignResponseDto.from(campaign)
    }

    @Transactional(readOnly = true)
    fun getAllCampaigns(): List<CampaignResponseDto> =
        campaignRepository
            .findAll()
            .map { CampaignResponseDto.from(it) }

    @Transactional
    fun updateCampaign(
        id: Long,
        dto: CampaignUpdateDto,
    ): CampaignResponseDto {
        val campaign =
            campaignRepository.findById(id).orElseThrow {
                CampaignNotFoundException("캠페인을 찾을 수 없습니다. ID: $id")
            }

        campaign.apply {
            dto.name?.let { name = it }
            dto.description?.let { description = it }
            dto.status?.let { status = it }
            dto.startDate?.let { startDate = it }
            dto.endDate?.let { endDate = it }
            dto.movieId?.let { movieId = it }
            dto.movieTitle?.let { movieTitle = it }
            dto.bannerImageUrl?.let { bannerImageUrl = it }
            dto.targetUrl?.let { targetUrl = it }
        }

        return CampaignResponseDto.from(campaign)
    }

    @Transactional
    fun deleteCampaign(id: Long) {
        val campaign =
            campaignRepository.findById(id).orElseThrow {
                CampaignNotFoundException("캠페인을 찾을 수 없습니다. ID: $id")
            }
        campaignRepository.delete(campaign)
    }
}
