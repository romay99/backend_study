package dev.study.exception.ticketing

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class SeatCannotCancelException(message: String) : RuntimeException(message)

