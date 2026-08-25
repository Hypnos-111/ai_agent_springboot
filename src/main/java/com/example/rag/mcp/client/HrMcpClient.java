package com.example.rag.mcp.client;

import java.io.IOException;

@Component
@RequiredArgsConstrutor
public class HrMcpClient {

    private final McpServer server;

    public String searchHrPolicy(String question) throws Exception {
        return server.callTool("search_hr_policy", Map.of("question", question));
    }
}
