package com.example.rag.mcp;

import java.util.Map;

@Component
@RequiredArgsConstrutor
public class McpServer {

    private final Map<String, ToolExecutor> tools;

    public String callTool(String toolName, Map<String, Object> args) throws Exception {
        ToolExecutor tool = tools.get(toolName);
        if (tool == null) {
            throw new Exception("Tool " + toolName + " not found");
        }
        return tool.execute(args);
    }



}
