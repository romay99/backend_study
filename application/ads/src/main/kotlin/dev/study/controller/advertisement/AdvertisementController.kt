package dev.study.controller.advertisement

import dev.study.dto.advertisement.MultipleBannersResponseDto
import dev.study.dto.advertisement.SingleBannerResponseDto
import dev.study.service.advertisement.AdvertisementService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/advertisements")
class AdvertisementController(
    private val advertisementService: AdvertisementService
) {

    @GetMapping("/banner")
    fun getSingleBanner(): ResponseEntity<SingleBannerResponseDto> {
        val response = advertisementService.getSingleBanner()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/banners")
    fun getMultipleBanners(
        @RequestParam(defaultValue = "5") count: Int
    ): ResponseEntity<MultipleBannersResponseDto> {
        val response = advertisementService.getMultipleBanners(count)
        return ResponseEntity.ok(response)
    }
}
