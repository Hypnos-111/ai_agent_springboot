package com.example.rag.mcp.client;

import com.example.rag.repository.EmployeeLeaveRepository;

@Component
@RequiredArgsConstrutor
public class EmployeeMcpClient {

    private final McpServer server;

    public String getInfo(String question) {
        return server.callTool("get_employee_info", Map.of("question", question));
    }
}
