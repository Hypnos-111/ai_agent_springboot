package com.example.rag.service;

import com.example.rag.model.EmployeeLeave;
import com.example.rag.repository.EmployeeLeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final EmployeeLeaveRepository repository;

    public String getLeave(String employeeId) {


        EmployeeLeave leave = repository.findById(employeeId).orElseThrow();

        return "";
    }
}
