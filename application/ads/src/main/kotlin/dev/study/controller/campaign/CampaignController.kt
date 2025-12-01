package dev.study.controller.campaign

import dev.study.dto.campaign.CampaignCreateDto
import dev.study.dto.campaign.CampaignResponseDto
import dev.study.dto.campaign.CampaignUpdateDto
import dev.study.service.campaign.CampaignService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/campaign")
class CampaignController(
    private val campaignService: CampaignService
) {

    @PostMapping
    fun createCampaign(@RequestBody dto: CampaignCreateDto): ResponseEntity<CampaignResponseDto> {
        val response = campaignService.createCampaign(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{id}")
    fun getCampaign(@PathVariable id: Long): ResponseEntity<CampaignResponseDto> {
        val response = campaignService.getCampaign(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getAllCampaigns(): ResponseEntity<List<CampaignResponseDto>> {
        val response = campaignService.getAllCampaigns()
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun updateCampaign(
        @PathVariable id: Long,
        @RequestBody dto: CampaignUpdateDto
    ): ResponseEntity<CampaignResponseDto> {
        val response = campaignService.updateCampaign(id, dto)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteCampaign(@PathVariable id: Long): ResponseEntity<Void> {
        campaignService.deleteCampaign(id)
        return ResponseEntity.noContent().build()
    }
}
