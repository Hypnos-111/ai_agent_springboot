package com.example.rag.service;

public record TokenResult(
        long[] inputIds,
        long[] attentionMask

) {
}
