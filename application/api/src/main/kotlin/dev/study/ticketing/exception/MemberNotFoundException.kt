package dev.study.ticketing.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class MemberNotFoundException(message: String) : RuntimeException(message) {

}
