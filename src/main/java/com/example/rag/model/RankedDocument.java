package com.example.rag.model;

import org.springframework.ai.document.Document;

public record RankedDocument(
        Document document,
        float score
) {
}
