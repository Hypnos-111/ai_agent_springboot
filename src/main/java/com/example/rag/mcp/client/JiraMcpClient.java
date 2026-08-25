package com.example.rag.mcp.client;

import com.example.rag.mcp.McpServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JiraMcpClient {

    private final McpServer server;

    public String search(String question) throws Exception {
        return server.callTool("jira_search", Map.of("question", question));
    }
}
