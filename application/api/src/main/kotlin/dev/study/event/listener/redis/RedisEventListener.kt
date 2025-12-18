package dev.study.event.listener.redis

import dev.study.domain.showSeat.SeatStatus
import dev.study.logging.logger
import dev.study.repository.showSeatRepository.ShowSeatRepository
import jakarta.annotation.PostConstruct
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Component
class RedisEventListener(
    private val showSeatRepository: ShowSeatRepository,
    private val redissonClient: RedissonClient
) {
    private val logger = logger()

    @PostConstruct
    fun subscribeExpiredEvents() {
        val topic = redissonClient.getTopic("__keyevent@0__:expired")
        topic.addListener(String::class.java) { _, expiredKey ->
            if (expiredKey.startsWith("hold:seat:")) {
                // hold:seat:${movieId}:${screenNumber}:${col}:${num}
                val split = expiredKey.split(":")
                val movieId = split[2].toLong()
                val screenNumber = split[3].toInt()
                val col = split[4].toString()
                val num = split[5].toInt()

                val seat = showSeatRepository.findShowSeat(movieId, col, num, screenNumber)

                if (seat != null) {
                    seat.status = SeatStatus.AVAILABLE
                    showSeatRepository.save(seat)
                    logger.info("TTL 만료: 좌석 $movieId-$col-$num 상태를 AVAILABLE로 복귀")
                }
            }
        }
    }
}
