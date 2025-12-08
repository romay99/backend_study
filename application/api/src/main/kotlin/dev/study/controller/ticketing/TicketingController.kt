package dev.study.controller.ticketing

import dev.study.dto.seat.SeatLockDTO
import dev.study.dto.showSeat.ShowSeatDto
import dev.study.dto.ticketing.TicketingCancelDto
import dev.study.dto.ticketing.TicketingTryDto
import dev.study.service.showSeat.ShowSeatService
import dev.study.service.ticketing.TicketingService
import io.swagger.v3.oas.annotations.Operation
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
    @Operation(summary = "좌석 점유", description = "좌석을 예매하기 위해 먼저 좌석을 점유합니다.")
    fun holdSeat(@RequestBody dto: SeatLockDTO): ResponseEntity<String> {
        ticketingService.holdSeat(dto)
        return ResponseEntity.ok().build()
    }

    /**
     * 좌석 예매하는 메서드
     * 점유 상태인 좌석만 예매 가능하다
     */
    @Operation(summary = "좌석 예매", description = "점유된 좌석을 예매합니다.")
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
    @Operation(summary = "좌석 예매 취소", description = "예매 완료된 좌석을 취소합니다.")
    fun cancelTicket(@RequestBody dto: TicketingCancelDto): ResponseEntity<String> {
        ticketingService.cancelTicket(dto)
        return ResponseEntity.ok().build()
    }

    /**
     * 하나의 상영 스케줄에 관해서 예매 가능한 좌석들을 조회하는 메서드
     */
    @GetMapping()
    @Operation(summary = "예매 가능 좌석 조회", description = "특정 상영 스케줄의 예매 가능한 좌석을 조회합니다 .")
    fun getAvailableShowSeats(@RequestParam movieId: Long): ResponseEntity<List<ShowSeatDto>> {
        val seats = showSeatService.getAvailableShowSeats(movieId)
        return ResponseEntity.ok(seats)
    }
}
