package com.example.employee.leave.request.exception

import org.springframework.http.HttpStatus

class ApprovalException(message: String = "Approval exception"): CustomException(message) {
    override val statusCode = HttpStatus.BAD_REQUEST
}