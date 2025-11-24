package dev.study.advertisement.dto

data class MultipleBannersResponseDto(
    val banners: List<BannerDto>,
    val count: Int
)
