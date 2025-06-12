package com.example.employee.leave.request.handler

import com.example.employee.leave.request.service.LeaveRequestService
import org.springframework.stereotype.Component

@Component
class LeaveRequestHandler(
    private val service: LeaveRequestService
) {

}