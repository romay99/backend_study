package dev.study.exception.seat

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class SeatAlreadyOccupiedException(message: String) : RuntimeException(message)
