package com.example.rag.model;

@Entity
@Table(name = "employee_leave")
@Data
public class EmployeeLeave {
    @Id
    private String employeeId;
    private Integer annualLeave;
    private Integer compensatoryLeave;
}
