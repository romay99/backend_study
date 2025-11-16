package dev.study.ticketing.controller

import dev.study.ticketing.service.TicketingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ticketing")
class TicketingController(
    private val ticketingService: TicketingService
) {
    @PostMapping
    fun reserveTicket(movieId: Long, memberId: Long): ResponseEntity<String> {
        ticketingService.reserveTicket(movieId,memberId)
        return ResponseEntity.ok().build()
    }

}