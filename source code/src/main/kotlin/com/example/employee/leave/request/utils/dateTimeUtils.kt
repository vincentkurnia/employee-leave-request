package com.example.employee.leave.request.utils

import java.time.LocalDate
import java.time.LocalDateTime

fun getCurrentDate(): LocalDate {
    return LocalDate.now()
}

fun getCurrentDateTime(): LocalDateTime {
    return LocalDateTime.now()
}