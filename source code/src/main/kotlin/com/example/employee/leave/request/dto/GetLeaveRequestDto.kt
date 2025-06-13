package com.example.employee.leave.request.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GetLeaveRequestDto(
    @JsonProperty("employee_id")
    val employeeId: Int,
    val status: Int,
    val type: Int
)