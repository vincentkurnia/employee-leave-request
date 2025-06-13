package com.example.employee.leave.request.exception

import org.springframework.http.HttpStatus

class LeaveRequestNotFoundException: CustomException("Leave request not found") {
    override val statusCode = HttpStatus.NOT_FOUND
}