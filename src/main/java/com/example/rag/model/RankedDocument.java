package com.example.rag.model;

import javax.swing.text.Document;

public record RankedDocument(
        Document document,
        float score
) {
}
