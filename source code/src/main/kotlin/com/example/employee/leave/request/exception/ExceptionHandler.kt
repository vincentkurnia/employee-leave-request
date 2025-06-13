package com.example.employee.leave.request.exception

import com.example.employee.leave.request.dto.ErrorResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebExchange

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException::class)
    fun handleEmployeeNotFoundException(e: EmployeeNotFoundException, exchange: ServerWebExchange): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity(e.toErrorResponseDto(exchange), e.statusCode)
    }

    @ExceptionHandler(NotAuthorizedException::class)
    fun handleNotAuthorizedException(e: NotAuthorizedException, exchange: ServerWebExchange): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity(e.toErrorResponseDto(exchange), e.statusCode)
    }

    @ExceptionHandler(LeaveRequestNotFoundException::class)
    fun handleEmployeeNotFoundException(e: LeaveRequestNotFoundException, exchange: ServerWebExchange): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity(e.toErrorResponseDto(exchange), e.statusCode)
    }

    @ExceptionHandler(ApprovalException::class)
    fun handleEmployeeNotFoundException(e: ApprovalException, exchange: ServerWebExchange): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity(e.toErrorResponseDto(exchange), e.statusCode)
    }

    private fun CustomException.toErrorResponseDto(exchange: ServerWebExchange): ErrorResponseDto {
        return ErrorResponseDto(
            status = this.statusCode.value(),
            message = this.message,
            path = exchange.request.path.toString()
        )
    }
}