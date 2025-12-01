package dev.study.scheduler.showSeat

import dev.study.logging.logger
import dev.study.service.showSeat.ShowSeatService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ShowSeatScheduler (
    private val showSeatService: ShowSeatService
){
    private val logger = logger()

    /**
     * 1시간마다 현재 시간 기준으로 이미 상영에 들어간 ShowSeat 들을 삭제한다
     */
    @Scheduled(cron = "0 0 * * * *")
    fun removePastShowSeats(){
        val row = showSeatService.removePastShowSeats()
        logger.info("deleted showSeat Counts : $row")
    }
}
