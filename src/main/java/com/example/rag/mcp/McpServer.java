package com.example.rag.mcp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
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
