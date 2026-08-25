package com.example.rag.mcp;

import java.util.Map;

public interface ToolExecutor {
    String execute(Map<String, Object> args) throws Exception;
}
