package dev.study.controller.ticketing

import dev.study.dto.seat.SeatLockDTO
import dev.study.dto.showSeat.ShowSeatDto
import dev.study.dto.ticketing.TicketingCancelDto
import dev.study.dto.ticketing.TicketingTryDto
import dev.study.service.showSeat.ShowSeatService
import dev.study.service.ticketing.TicketingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ticketing")
class TicketingController(
    private val ticketingService: TicketingService,
    private val showSeatService: ShowSeatService
) {

    /**
     * Redisson 분산락을 이용한 좌석 점유 메서드
     * 좌석 점유 성공 / 예매 2가지 로직으로 분리
     */
    @PostMapping("/seat")
    fun holdSeat(@RequestBody dto: SeatLockDTO): ResponseEntity<String> {
        ticketingService.holdSeat(dto)
        return ResponseEntity.ok().build()
    }

    /**
     * 좌석 예매하는 메서드
     * 점유 상태인 좌석만 예매 가능하다
     */
    @PostMapping("/reserve")
    fun reserveTicket(@RequestBody dto: TicketingTryDto): ResponseEntity<String> {
        ticketingService.reserveTicket(dto)
        return ResponseEntity.ok().build()
    }

    /**
     * 좌석 예매 취소하는 메서드
     * 예매된 건에서만 작동한다
     */
    @PostMapping("/cancel")
    fun cancelTicket(@RequestBody dto: TicketingCancelDto): ResponseEntity<String> {
        ticketingService.cancelTicket(dto)
        return ResponseEntity.ok().build()
    }

    /**
     * 하나의 상영 스케줄에 관해서 예매 가능한 좌석들을 조회하는 메서드
     */
    @GetMapping()
    fun getAvailableShowSeats(@RequestParam movieId: Long): ResponseEntity<List<ShowSeatDto>> {
        val seats = showSeatService.getAvailableShowSeats(movieId)
        return ResponseEntity.ok(seats)
    }
}
