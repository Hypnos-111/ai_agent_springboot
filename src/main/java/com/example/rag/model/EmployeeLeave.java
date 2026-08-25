package com.example.rag.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "employee_leave")
@Data
public class EmployeeLeave {
    @Id
    private String employeeId;
    private Integer annualLeave;
    private Integer compensatoryLeave;
}
