package dev.study.controller.ticketing

import TicketingService
import dev.study.dto.ticketing.TicketingTryDto
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
    @PostMapping
    fun reserveTicket(@RequestBody dto: TicketingTryDto): ResponseEntity<String> {
        ticketingService.reserveTicket(dto)
        return ResponseEntity.ok().build()
    }
}