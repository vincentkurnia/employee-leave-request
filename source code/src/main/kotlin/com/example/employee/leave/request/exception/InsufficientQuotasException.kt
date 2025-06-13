package com.example.employee.leave.request.exception

import org.springframework.http.HttpStatus

class InsufficientQuotasException: CustomException("Insufficient leave quotas") {
    override val statusCode = HttpStatus.BAD_REQUEST
}