package com.example.rag.mcp.client;

@Component
@RequiredArgsConstrutor
public class LeaveMcpClient {

    private final McpServer server;

    public String getLeave(String question) {
        return server.callTool("get_employee_leave", Map.of("question", question));
    }
}
