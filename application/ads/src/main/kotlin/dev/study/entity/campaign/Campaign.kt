package dev.study.entity.campaign

import dev.study.domain.campaign.CampaignStatus
import dev.study.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "campaign")
open class Campaign(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,
    open var name: String,
    open var description: String? = null,
    @Enumerated(EnumType.STRING)
    open var status: CampaignStatus = CampaignStatus.SCHEDULED,
    open var startDate: LocalDateTime,
    open var endDate: LocalDateTime,
    open var movieId: Long,
    open var movieTitle: String,
    open var bannerImageUrl: String,
    open var targetUrl: String? = null,
) : BaseEntity()
