package com.example.employee.leave.request.repository

import com.example.employee.leave.request.dto.GetLeaveRequestDto
import com.example.employee.leave.request.dto.GetLeaveRequestForApproverDto
import com.example.employee.leave.request.model.LeaveRequest
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.coroutines.flow.toList
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.function.BiFunction

@Component
class LeaveRequestRepository(
    private val client: DatabaseClient
) {
    private val leaveRequestMappingFunc = BiFunction<Row, RowMetadata, LeaveRequest> { row, _ ->
        LeaveRequest(
            row.get("id") as? Int,
            row.get("start_date") as LocalDate,
            row.get("end_date") as LocalDate,
            row.get("request_date") as LocalDate,
            row.get("detail") as String,
            row.get("employee") as Int,
            row.get("type") as Int,
            row.get("status") as Int
        )
    }

    suspend fun submitLeaveRequest(payload: LeaveRequest): Long {
        val query = """
            INSERT INTO leave_request (start_date, end_date, request_date, detail, employee, type, status)
            VALUES (:startDate, :endDate, :requestDate, :detail, :employee, :type, :status)
        """.trimIndent()

        return client.sql(query)
            .bind("startDate", payload.startDate)
            .bind("endDate", payload.endDate)
            .bind("requestDate", payload.requestDate)
            .bind("detail", payload.detail)
            .bind("employee", payload.employee)
            .bind("type", payload.type)
            .bind("status", payload.status)
            .fetch()
            .awaitRowsUpdated()
    }

    suspend fun getLeaveTypes(): List<LeaveRequest.Type> {
        val query = "SELECT * FROM leave_types"

        return client.sql(query)
            .map { row ->
                LeaveRequest.Type(
                    row.get("id") as Int,
                    row.get("type") as String
                )
            }
            .flow()
            .toList()
    }

    suspend fun getLeaveRequestStatuses(): List<LeaveRequest.Status> {
        val query = "SELECT * FROM leave_request_statuses"

        return client.sql(query)
            .map { row ->
                LeaveRequest.Status(
                    row.get("id") as Int,
                    row.get("status") as String
                )
            }
            .flow()
            .toList()
    }

    suspend fun getQuotas(id: Int): Int? {
        val query = "SELECT quotas FROM leave_quotas WHERE employee_id = :id"

        return client.sql(query)
            .bind("id", id)
            .mapValue(Int::class.java)
            .awaitOneOrNull()
    }

    suspend fun getRequest(id: Int): LeaveRequest? {
        val query = "SELECT * FROM leave_request WHERE id = :id"

        return client.sql(query)
            .bind("id", id)
            .map(leaveRequestMappingFunc)
            .awaitSingleOrNull()
    }

    suspend fun getRequests(payload: GetLeaveRequestDto): List<LeaveRequest> {
        val query = "SELECT * FROM leave_request WHERE employee = :employee AND status = :status AND type = :type"

        return client.sql(query)
            .bind("employee", payload.employeeId)
            .bind("status", payload.status)
            .bind("type", payload.type)
            .map(leaveRequestMappingFunc)
            .flow()
            .toList()
    }

    suspend fun getRequestForApprover(payload: GetLeaveRequestForApproverDto): List<LeaveRequest> {
        val query = """SELECT * FROM leave_request 
            WHERE employee IN (SELECT id from employees WHERE leader_id = :leaderId)
            AND status = :status
        """.trimIndent()

        return client.sql(query)
            .bind("leaderId", payload.approverId)
            .bind("status", payload.status)
            .map(leaveRequestMappingFunc)
            .flow()
            .toList()
    }

    suspend fun updateQuotas(id: Int, quotas: Int): Long {
        val query = "UPDATE leave_quotas SET quotas = :quotas WHERE employee_id = :id"

        return client.sql(query)
            .bind("quotas", quotas)
            .bind("id", id)
            .fetch()
            .awaitRowsUpdated()
    }

    suspend fun updateRequestStatus(id: Int, employee: Int, status: Int): Long {
        val query = "UPDATE leave_request SET status = :status WHERE id = :id AND employee = :employee"

        return client.sql(query)
            .bind("status", status)
            .bind("id", id)
            .bind("employee", employee)
            .fetch()
            .awaitRowsUpdated()
    }
}