package dev.study.handler

import dev.study.exception.member.MemberNotFoundException
import dev.study.exception.member.NotEnoughAmountException
import dev.study.exception.seat.SeatAlreadyOccupiedException
import dev.study.exception.seat.SeatNotFoundException
import dev.study.exception.ticketing.SeatCannotCancelException
import dev.study.exception.ticketing.TicketingNotStartedException
import dev.study.logging.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = logger()
    /**
     * 전역 예외 처리
     */
    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(e: Exception): ResponseEntity<Any> {
        logger.error("GlobalExceptionHandler: ", e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }

    /**
     * <예매 시간 이전 예매 시도> 예외 처리
     */
    @ExceptionHandler(TicketingNotStartedException::class)
    fun ticketingNotStartedExceptionHandler(e: TicketingNotStartedException): ResponseEntity<Any> {
        logger.error("TicketingNotStartedException: ", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    /**
     * <사용자 정보가 존재하지 않을 때> 예외 처리
     */
    @ExceptionHandler(MemberNotFoundException::class)
    fun memberNotFoundExceptionHandler(e: MemberNotFoundException): ResponseEntity<Any> {
        logger.error("MemberNotFoundException: ", e)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    /**
     * <사용자 계좌에 돈이 충분하지 않을 때> 예외 처리
     */
    @ExceptionHandler(NotEnoughAmountException::class)
    fun notEnoughAmountExceptionHandler(e: NotEnoughAmountException): ResponseEntity<Any> {
        logger.error("NotEnoughAmountException: ", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    /**
     * <좌석 점유에 대한 Lock 을 획득하지 못했을 때> 예외 처리
     */
    @ExceptionHandler(SeatAlreadyOccupiedException::class)
    fun seatAlreadyOccupiedExceptionHandler(e: SeatAlreadyOccupiedException): ResponseEntity<Any> {
        logger.error("SeatAlreadyOccupiedException: ", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    /**
     * <좌석 정보를 찾을 수 없을 때> 예외 처리
     */
    @ExceptionHandler(SeatNotFoundException::class)
    fun seatNotFoundExceptionHandler(e: SeatNotFoundException): ResponseEntity<Any> {
        logger.error("SeatNotFoundException: ", e)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    /**
     * 예매된 좌석이 아닌 좌석을 취소하려고 할때 예외 처리
     */
    @ExceptionHandler(SeatCannotCancelException::class)
    fun seatCannotCancelExceptionHandler(e: SeatCannotCancelException): ResponseEntity<Any> {
        logger.error("SeatCannotCancelException: ", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }
}
