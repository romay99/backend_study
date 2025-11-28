package dev.study.controller.ticketing

import dev.study.dto.seat.SeatLockDTO
import dev.study.dto.ticketing.TicketingTryDto
import dev.study.service.ticketing.TicketingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ticketing")
class TicketingController(
    private val ticketingService: TicketingService
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
}