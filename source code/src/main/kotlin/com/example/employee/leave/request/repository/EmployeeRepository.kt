package com.example.employee.leave.request.repository

import com.example.employee.leave.request.model.Employee
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Component

@Component
class EmployeeRepository(
    private val client: DatabaseClient
) {
    suspend fun getEmployee(id: Int): Employee? {
        val query = """
            SELECT e.*, lq.quotas FROM employees e
            JOIN leave_quotas lq ON e.id = lq.employee_id
            WHERE e.id = :id
        """.trimIndent()

        return client.sql(query)
            .bind("id", id)
            .map { row ->
                Employee(
                    row.get("id") as Int,
                    row.get("first_name") as String,
                    row.get("last_name") as String,
                    row.get("email") as String,
                    row.get("leader_id") as? Int
                )
            }
            .awaitOneOrNull()
    }
}