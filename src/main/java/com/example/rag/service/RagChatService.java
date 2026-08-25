package com.example.rag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagChatService {

    private final RetrieverService retriever;
    private final BgeRerankerService reranker;
    private final ChatClient chatClient;

    public String ask(String question, String department) {
        String context = buildContext(question, department);

        return chatClient.prompt().system("").user(question).call().content();
    }

    public Flux<String> stream(String question, String department) {
        String context = buildContext(question, department);

        return chatClient.prompt().system("").user(question).stream().content();
    }

    private String buildContext(String question, String department) {
        List<Document> candidates = retriever.retrieve1(question, department);
        List<Document> docs = reranker.rerank(question, candidates);
        return docs.stream().map(Document::getText).collect(Collectors.joining("\n"));
    }
}
