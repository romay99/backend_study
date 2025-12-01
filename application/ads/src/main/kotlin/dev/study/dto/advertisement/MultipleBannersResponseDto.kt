package dev.study.dto.advertisement

data class MultipleBannersResponseDto(
    val banners: List<BannerDto>,
    val count: Int
)
