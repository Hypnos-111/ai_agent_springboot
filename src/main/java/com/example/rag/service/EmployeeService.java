package com.example.rag.service;

import com.example.rag.dto.EmployeeInfo;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    public String getInfo(String question) {
        EmployeeInfo info = new EmployeeInfo("E001", "John", "IT", "Senior Engineer", "David");

        return "";
    }
}
