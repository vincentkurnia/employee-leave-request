package com.example.employee.leave.request.model

data class Employee(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val leaderId: Int?
)