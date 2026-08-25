package com.example.rag.service;

import com.example.rag.RagApplication;

import javax.swing.text.Document;
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

    public String stream(String question, String department) {
        String context = buildContext(question, department);

        return chatClient.prompt().system("").user(question).stream().content();
    }

    private String buildContext(String question, String department) {
        List<Document> candidates = retriever.retrieve1(question, department);
        List<Document> docs = reranker.rerank(question, candidates);
        return docs.stream().map(Document::getText).collect(Collectors.joining("\n"));
    }
}
