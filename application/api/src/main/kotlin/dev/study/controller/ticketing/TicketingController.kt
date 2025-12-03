package dev.study.controller.ticketing

import dev.study.dto.seat.SeatLockDTO
import dev.study.dto.showSeat.ShowSeatDto
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

    @PostMapping("/seat")
    fun holdSeat(@RequestBody dto: SeatLockDTO): ResponseEntity<String> {
        ticketingService.holdSeat(dto)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/reserve")
    fun reserveTicket(@RequestBody dto: TicketingTryDto): ResponseEntity<String> {
        ticketingService.reserveTicket(dto)
        return ResponseEntity.ok().build()
    }

    @GetMapping()
    fun getAvailableShowSeats(@RequestParam movieId: Long): ResponseEntity<List<ShowSeatDto>> {
        val seats = showSeatService.getAvailableShowSeats(movieId)
        return ResponseEntity.ok(seats)
    }
}
