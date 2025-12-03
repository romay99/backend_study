package dev.study.service.movie

import dev.study.dto.movie.MovieScheduleUploadDto
import dev.study.entity.movie.Movie
import dev.study.entity.showSeat.ShowSeat
import dev.study.logging.logger
import dev.study.repository.movie.MovieRepository
import dev.study.repository.seat.SeatRepository
import dev.study.repository.showSeatRepository.ShowSeatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MovieService (
    private val movieRepository: MovieRepository,
    private val showSeatRepository: ShowSeatRepository,
    private val seatRepository: SeatRepository
) {
    private val logger = logger()

    /**
     * 영화 스케줄 업로드하는 메서드
     * 영화 스케줄이 생성될때 예매 가능한 좌석 (ShowSeat) 데이터들이 insert 된다
     */
    @Transactional
    fun uploadMovieSchedule(dto: MovieScheduleUploadDto) {
        val movie = Movie(name = dto.name, time = dto.time)
        movieRepository.save(movie)

        val showSeatList = seatRepository.findAllByScreenNumber(dto.screenNumber)
            .map { seat -> ShowSeat(movie = movie, seat = seat) }

        showSeatRepository.saveAll(showSeatList)
        logger.info("${dto.name}, ${dto.time} Movie schedule uploaded successfully")
    }
}
