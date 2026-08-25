package com.example.rag.service;

@Service
public class LeaveService {

    private final EmployeeLeaveRepository repository;

    public String getLeave(String employeeId) {


        EmployeeLeave leave = repository.findById(employeeId).orElseThrow();

        return "";
    }
}
