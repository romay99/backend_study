package dev.study.ticketing.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class TicketingNotStartedException(message : String) : RuntimeException(message) {

}
