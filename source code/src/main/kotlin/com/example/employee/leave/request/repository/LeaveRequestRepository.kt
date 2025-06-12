package com.example.employee.leave.request.repository

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component

@Component
class LeaveRequestRepository(
    private val client: DatabaseClient
) {

}