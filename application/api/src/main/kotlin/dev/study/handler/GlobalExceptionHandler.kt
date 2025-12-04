package dev.study.handler

import dev.study.dto.exception.CommonErrorResponse
import dev.study.exception.member.MemberNotFoundException
import dev.study.exception.member.NotEnoughAmountException
import dev.study.exception.seat.SeatAlreadyOccupiedException
import dev.study.exception.seat.SeatNotFoundException
import dev.study.exception.ticketing.SeatCannotCancelException
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
    fun globalExceptionHandler(e: Exception): ResponseEntity<CommonErrorResponse> {
        logger.error("GlobalExceptionHandler: ", e)

        val body = CommonErrorResponse(
            code = "INTERNAL_SERVER_ERROR",
            message = "서버 내부 오류가 발생했습니다.",
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body)
    }

    /**
     * <사용자 정보가 존재하지 않을 때> 예외 처리
     */
    @ExceptionHandler(MemberNotFoundException::class)
    fun memberNotFoundExceptionHandler(e: MemberNotFoundException): ResponseEntity<CommonErrorResponse> {
        logger.error("MemberNotFoundException: ", e)

        val body = CommonErrorResponse(
            code = "MEMBER_NOT_FOUND",
            message = "사용자를 찾을 수 없습니다.",
            status = HttpStatus.NOT_FOUND.value(),
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body)
    }

    /**
     * <사용자 계좌에 돈이 충분하지 않을 때> 예외 처리
     */
    @ExceptionHandler(NotEnoughAmountException::class)
    fun notEnoughAmountExceptionHandler(e: NotEnoughAmountException): ResponseEntity<CommonErrorResponse> {
        logger.error("NotEnoughAmountException: ", e)

        val body = CommonErrorResponse(
            code = "NOT_ENOUGH_AMOUNT",
            message = "계좌에 잔액이 충분하지 않습니다.",
            status = HttpStatus.BAD_REQUEST.value(),
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    /**
     * <좌석 점유에 대한 Lock 을 획득하지 못했을 때> 예외 처리
     */
    @ExceptionHandler(SeatAlreadyOccupiedException::class)
    fun seatAlreadyOccupiedExceptionHandler(e: SeatAlreadyOccupiedException): ResponseEntity<CommonErrorResponse> {
        logger.error("SeatAlreadyOccupiedException: ", e)

        val body = CommonErrorResponse(
            code = "SEAT_ALREADY_OCCUPIED",
            message = "좌석 점유에 실패했습니다.",
            status = HttpStatus.BAD_REQUEST.value(),
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    /**
     * <좌석 정보를 찾을 수 없을 때> 예외 처리
     */
    @ExceptionHandler(SeatNotFoundException::class)
    fun seatNotFoundExceptionHandler(e: SeatNotFoundException): ResponseEntity<CommonErrorResponse> {
        logger.error("SeatNotFoundException: ", e)

        val body = CommonErrorResponse(
            code = "SEAT_NOT_FOUND",
            message = "존재하지 않는 좌석입니다.",
            status = HttpStatus.NOT_FOUND.value(),
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body)
    }

    /**
     * 예매된 좌석이 아닌 좌석을 취소하려고 할때 예외 처리
     */
    @ExceptionHandler(SeatCannotCancelException::class)
    fun seatCannotCancelExceptionHandler(e: SeatCannotCancelException): ResponseEntity<CommonErrorResponse> {
        logger.error("SeatCannotCancelException: ", e)

        val body = CommonErrorResponse(
            code = "SEAT_CANNOT_CANCEL",
            message = "취소할 수 없는 좌석입니다.",
            status = HttpStatus.BAD_REQUEST.value(),
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }
}
