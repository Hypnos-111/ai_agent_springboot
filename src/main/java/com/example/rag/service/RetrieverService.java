package com.example.rag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieverService {

    private final VectorStore vectorStore;

    public List<Document> retrieve(String query) {
        return vectorStore.similaritySearch(
                SearchRequest.builder().query(query)
                        .topK(30).similarityThreshold(0.75).build()
        );
    }

    public List<Document> retrieve1(String query, String department) {
        SearchRequest request = SearchRequest.builder().query(query)
                .filterExpression("department == '%s' && type == 'FAQ'".formatted(department))
                .topK(30).similarityThreshold(0.75).build();
        return vectorStore.similaritySearch(request);
    }

}
