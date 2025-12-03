package dev.study.controller.movie

import dev.study.dto.movie.MovieScheduleUploadDto
import dev.study.service.movie.MovieService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movie")
class MovieController(
    val movieService: MovieService
) {
    /**
     * 영화 스케줄 업로드하는 메서드
     * 영화 스케줄이 생성될때 예매 가능한 좌석 (ShowSeat) 데이터들이 insert 된다
     */
    @PostMapping()
    fun uploadMovieSchedule(@RequestBody dto: MovieScheduleUploadDto): ResponseEntity<String> {
        movieService.uploadMovieSchedule(dto)
        return ResponseEntity("Movie schedule uploaded successfully", HttpStatus.OK)
    }
}
