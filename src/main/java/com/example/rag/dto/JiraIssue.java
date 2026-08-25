package com.example.rag.dto;

public record JiraIssue(
        String key,
        String summary,
        String status
) {
}
