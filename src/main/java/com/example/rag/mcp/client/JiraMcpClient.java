package com.example.rag.mcp.client;

@Component
@RequiredArgsConstrutor
public class JiraMcpClient {

    private final McpServer server;

    public String search(String question) throws Exception {
        return server.callTool("jira_search", Map.of("question", question));
    }
}
