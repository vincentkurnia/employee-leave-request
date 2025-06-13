package com.example.employee.leave.request.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CancelLeaveRequestDto(
    @JsonProperty("employee_id")
    val employeeId: Int,
    @JsonProperty("request_id")
    val requestId: Int
)
