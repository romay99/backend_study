package dev.study.exception.member

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class NotEnoughAmountException(message: String) : RuntimeException(message) {

}
