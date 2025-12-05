package dev.study.repository.campaign

import dev.study.domain.campaign.CampaignStatus
import dev.study.entity.campaign.Campaign
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CampaignRepository : JpaRepository<Campaign, Long> {
    @Query("SELECT c FROM Campaign c WHERE c.status = :status ORDER BY c.createdAt DESC")
    fun findFirstByStatus(
        @Param("status") status: CampaignStatus,
    ): Campaign?

    fun findByStatus(
        status: CampaignStatus,
        pageable: Pageable,
    ): Page<Campaign>
}
