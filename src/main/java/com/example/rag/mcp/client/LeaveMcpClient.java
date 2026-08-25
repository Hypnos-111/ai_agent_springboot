package com.example.rag.mcp.client;

import com.example.rag.mcp.McpServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LeaveMcpClient {

    private final McpServer server;

    public String getLeave(String question) throws Exception {
        return server.callTool("get_employee_leave", Map.of("question", question));
    }
}
