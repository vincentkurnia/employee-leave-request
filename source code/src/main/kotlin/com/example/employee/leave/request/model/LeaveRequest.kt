package com.example.employee.leave.request.model

import java.time.LocalDate

data class LeaveRequest(
    val id: Int? = null,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val requestDate: LocalDate,
    val detail: String,
    val employee: Int,
    val type: Int,
    val status: Int
) {
    data class Type(
        val id: Int,
        val type: String
    )

    data class Status(
        val id: Int,
        val status: String
    )
}