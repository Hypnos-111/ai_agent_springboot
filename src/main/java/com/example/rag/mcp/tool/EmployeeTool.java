package com.example.rag.mcp.tool;

import com.example.rag.mcp.ToolExecutor;
import com.example.rag.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("get_employee_info")
@RequiredArgsConstructor
public class EmployeeTool implements ToolExecutor {

    private final EmployeeService employeeService;

    @Override
    public String execute(Map<String, Object> args) throws Exception {
        String question = (String) args.get("question");
        return employeeService.getInfo(question);
    }
}
