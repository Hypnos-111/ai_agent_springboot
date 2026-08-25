package com.example.rag.dto;

public record EmployeeInfo(
        String employeeId,
        String name,
        String department,
        String title,
        String manager
) {
}
