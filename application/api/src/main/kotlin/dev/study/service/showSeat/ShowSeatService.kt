package dev.study.service.showSeat

import dev.study.repository.showSeatRepository.ShowSeatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ShowSeatService (
    private val showSeatRepository : ShowSeatRepository
){
    /**
     * ShowSeatScheduler 에서 1시간마다 실행하는 메서드
     * 기존에 존재하는 ShowSeat 중에 이미 상영에 들어간 ShowSeat 들을 삭제한다
     */
    @Transactional
    fun removePastShowSeats() : Int{
        val nowTime = LocalDateTime.now()
        return showSeatRepository.removePastShowSeats(nowTime)
    }
}

