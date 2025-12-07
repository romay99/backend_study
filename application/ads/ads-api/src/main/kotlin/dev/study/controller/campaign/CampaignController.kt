package dev.study.controller.campaign

import dev.study.dto.campaign.CampaignCreateDto
import dev.study.dto.campaign.CampaignResponseDto
import dev.study.dto.campaign.CampaignUpdateDto
import dev.study.service.campaign.CampaignService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/campaign")
class CampaignController(
    private val campaignService: CampaignService,
) {
    @PostMapping
    fun createCampaign(
        @RequestBody dto: CampaignCreateDto,
    ): ResponseEntity<CampaignResponseDto> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(campaignService.createCampaign(dto))

    @GetMapping("/{id}")
    fun getCampaign(
        @PathVariable id: Long,
    ): ResponseEntity<CampaignResponseDto> =
        ResponseEntity
            .ok()
            .body(campaignService.getCampaign(id))

    @GetMapping
    fun getAllCampaigns(): ResponseEntity<List<CampaignResponseDto>> =
        ResponseEntity
            .ok()
            .body(campaignService.getAllCampaigns())

    @PutMapping("/{id}")
    fun updateCampaign(
        @PathVariable id: Long,
        @RequestBody dto: CampaignUpdateDto,
    ): ResponseEntity<CampaignResponseDto> =
        ResponseEntity
            .ok()
            .body(campaignService.updateCampaign(id, dto))

    @DeleteMapping("/{id}")
    fun deleteCampaign(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        campaignService.deleteCampaign(id)
        return ResponseEntity
            .noContent()
            .build()
    }
}
