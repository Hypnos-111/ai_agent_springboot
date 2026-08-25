package com.example.rag.mcp.client;

import com.example.rag.mcp.McpServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class HrMcpClient {

    private final McpServer server;

    public String searchHrPolicy(String question) throws Exception {
        return server.callTool("search_hr_policy", Map.of("question", question));
    }
}
