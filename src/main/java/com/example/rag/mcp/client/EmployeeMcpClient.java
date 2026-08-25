package com.example.rag.mcp.client;

import com.example.rag.mcp.McpServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmployeeMcpClient {

    private final McpServer server;

    public String getInfo(String question) throws Exception {
        return server.callTool("get_employee_info", Map.of("question", question));
    }
}
