# Features

### GET "/api/employee/{id}"

---
- **Payload**:
  - Path variables:
    - *id* -> employee id
- **Features**: retrieve employee data
<br><br>

### GET "/api/employee/leave/request"

---
- **Payload**:
  - Body:  
    ```json
    {
      "employee_id": int,
      "status": int,
      "type": int 
    }
    ```
- **Features**: retrieve employee's leave request list by type & status
<br><br>

### POST "/api/employee/leave/request"

---
- **Payload**:
    - Body:
      ```json
      {
        "employee_id": int,
        "start_date": dd/MM/yyyy,
        "end_date": dd/MM/yyyy,
        "detail": string,
        "type": int 
      }
      ```
- **Features**: submit employee's leave request
<br><br>

### POST "/api/employee/leave/request/cancel"

---
- **Payload**:
    - Body:
      ```json
      {
        "employee_id": int,
        "request_id": int
      }
      ```
- **Features**: cancel employee's leave request
<br><br>


### GET "/api/employee/leave/quotas/{id}"

---
- **Payload**:
    - Path variables:
        - *id* -> employee id
- **Features**: retrieve employee's leave quotas
<br><br>


### GET "/api/employee/leave/types"

---
- **Features**: retrieve leave request types
<br><br>


### GET "/api/employee/leave/statuses"

---
- **Features**: retrieve leave request statuses
<br><br>


### GET "/api/employee/leave/approval"

---
- **Payload**:
    - Body:
      ```json
      {
        "approver_id": int,
        "status": int
      }
      ```
- **Features**: retrieve leave request list assigned to the approver (leader) by status
<br><br>


### POST "/api/employee/leave/approval"

---
- **Payload**:
    - Body:
      ```json
      {
        "approver_id": int,
        "request_id": int,
        "approved": boolean
      }
      ```
- **Features**: approve/reject leave request (only approver/leader has access)
