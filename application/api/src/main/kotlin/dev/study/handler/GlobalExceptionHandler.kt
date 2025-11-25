package dev.study.handler

import dev.study.ticketing.exception.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    /**
     * 전역 예외 처리
     */
    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(e: Exception): ResponseEntity<Any> {
        return ResponseEntity.status(500).build()
    }

    /**
     * <예매 시간 이전 예매 시도> 예외 처리
     */
    @ExceptionHandler(TicketingNotStartedException::class)
    fun ticketingNotStartedExceptionHandler(e: TicketingNotStartedException): ResponseEntity<Any> {
        return ResponseEntity.status(400).build()
    }

    /**
     * <사용자 정보가 존재하지 않을 때> 예외 처리
     */
    @ExceptionHandler(MemberNotFoundException::class)
    fun memberNotFoundExceptionHandler(e: MemberNotFoundException): ResponseEntity<Any> {
        return ResponseEntity.status(404).build()
    }

    /**
     * <사용자 계좌에 돈이 충분하지 않을 때> 예외 처리
     */
    @ExceptionHandler(NotEnoughAmountException::class)
    fun notEnoughAmountExceptionHandler(e: NotEnoughAmountException): ResponseEntity<Any> {
        return ResponseEntity.status(400).build()
    }

    /**
     * <좌석 점유에 대한 Lock 을 획득하지 못했을 때> 예외 처리
     */
    @ExceptionHandler(SeatAlreadyOccupiedException::class)
    fun seatAlreadyOccupiedExceptionHandler(e: SeatAlreadyOccupiedException): ResponseEntity<Any> {
        return ResponseEntity.status(400).build()
    }

    /**
     * <좌석 정보를 찾을 수 없을 때> 예외 처리
     */
    @ExceptionHandler(SeatNotFoundException::class)
    fun seatNotFoundExceptionHandler(e: SeatNotFoundException): ResponseEntity<Any> {
        return ResponseEntity.status(404).build()
    }
}
