package com.example.employee.leave.request.dto

import com.example.employee.leave.request.utils.getCurrentDateTime
import java.time.LocalDateTime

data class ErrorResponseDto(
    val timestamp: LocalDateTime = getCurrentDateTime(),
    val status: Int,
    val message: String?,
    val path: String
)
