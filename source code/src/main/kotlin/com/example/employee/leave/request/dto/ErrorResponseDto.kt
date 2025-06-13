package com.example.employee.leave.request.dto

import java.time.LocalDateTime

data class ErrorResponseDto(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val message: String?,
    val path: String
)
