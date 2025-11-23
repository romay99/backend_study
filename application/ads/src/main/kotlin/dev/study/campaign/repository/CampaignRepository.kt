package dev.study.campaign.repository

import dev.study.campaign.domain.CampaignStatus
import dev.study.campaign.entity.Campaign
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CampaignRepository : JpaRepository<Campaign, Long> {
    fun findByStatus(status: CampaignStatus): List<Campaign>
}
