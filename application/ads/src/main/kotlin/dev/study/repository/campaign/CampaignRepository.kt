package dev.study.repository.campaign

import dev.study.domain.campaign.CampaignStatus
import dev.study.entity.campaign.Campaign
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CampaignRepository : JpaRepository<Campaign, Long> {
    fun findFirstByStatus(status: CampaignStatus): Campaign?
    fun findByStatus(status: CampaignStatus, pageable: Pageable): List<Campaign>
}
