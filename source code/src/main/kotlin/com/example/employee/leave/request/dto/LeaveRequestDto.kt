package com.example.employee.leave.request.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class LeaveRequestDto(
    @JsonProperty("employee_id")
    val employeeId: Int,
    @JsonProperty("start_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    val startDate: LocalDate,
    @JsonProperty("end_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    val endDate: LocalDate,
    val detail: String,
    val type: Int
)
