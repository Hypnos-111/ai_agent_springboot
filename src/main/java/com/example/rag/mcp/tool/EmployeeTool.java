package com.example.rag.mcp.tool;

import com.example.rag.service.EmployeeService;

import java.util.Map;

@Component("get_employee_info")
@RequiredArgsConstrutor
public class EmployeeTool implements ToolExecutor {

    private final EmployeeService employeeService;

    @Override
    public String execute(Map<String, Object> args) throws Exception {
        String question = (String) args.get("question");
        return employeeService.getInfo(question);
    }
}
