package com.example.rag.config;

import com.example.rag.service.*;
import com.example.rag.state.RagState;
import lombok.RequiredArgsConstructor;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.StateGraph;
import org.bsc.langgraph4j.action.AsyncNodeActionWithConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.bsc.langgraph4j.action.AsyncNodeActionWithConfig.node_async;

@Configuration
@RequiredArgsConstructor
public class RagGraphConfig {
    private final ChatClient chatClient;
    private final RetrieverService retriever;
    private final BgeRerankerService reranker;

    @Bean
    public CompiledGraph<RagState> ragGraph() throws Exception {
        StateGraph<RagState> graph = new StateGraph<>(RagState.SCHEMA, RagState::new);

        AsyncNodeActionWithConfig<RagState> rewriteAction = node_async((state, config) -> {
            String rewritten = chatClient.prompt("""
                 
                    """.formatted(state.question())).call().content();

            if (rewritten.isBlank()) {
                rewritten = state.question();
            }
            return Map.of(RagState.REWRITTEN_QUERY, rewritten);
        });
        graph.addNode("rewrite", rewriteAction);

        AsyncNodeActionWithConfig<RagState> recallAction = node_async((state, config) -> {
           List<Document> docs = retriever.retrieve1(state.rewrittenQuery(), state.department());
            return Map.of(RagState.RECALL_DOCS, docs);
        });
        graph.addNode("recall", recallAction);

        AsyncNodeActionWithConfig<RagState> rerankAction = node_async((state, config) -> {
            List<Document> candidates = state.recallDocs();
            List<Document> reranked = reranker.rerank(state.question(), candidates);
            return Map.of(RagState.RERANK_DOCS, reranked);
        });
        graph.addNode("rerank", rerankAction);

        AsyncNodeActionWithConfig<RagState> answerAction = node_async((state, config) -> {
            List<Document> docs = state.rerankDocs();
            String context = docs.stream().map(Document::getText).collect(Collectors.joining("\n"));
            String answer = chatClient.prompt("""
                  
                    """.formatted(context, state.question())).call().content();
            return Map.of(RagState.ANSWER, answer);
        });
        graph.addNode("answer", answerAction);

        graph.addEdge(StateGraph.START, "rewrite");
        graph.addEdge("rewrite", "recall");
        graph.addEdge("recall", "rerank");
        graph.addEdge("rerank", "answer");
        graph.addEdge("answer", StateGraph.END);

        return graph.compile();
    }
}
