package com.example.rag.exception;

import com.example.rag.mcp.ToolExecutor;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String message) {
}
