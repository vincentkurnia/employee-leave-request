package com.example.employee.leave.request.exception

import org.springframework.http.HttpStatus

abstract class CustomException(
    override val message: String? = null,
    override val cause: Throwable? = null
): Exception() {
    abstract val statusCode: HttpStatus
}