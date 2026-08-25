package com.example.rag.state;

import org.bsc.langgraph4j.state.Channel;
import org.bsc.langgraph4j.state.Channels;
import org.springframework.ai.document.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class RagState extends org.bsc.langgraph4j.state.AgentState {

    public static final String QUESTION = "question";
    public static final String REWRITTEN_QUERY = "rewrittenQuery";
    public static final String RECALL_DOCS = "recallDocs";
    public static final String RERANK_DOCS = "rerankDocs";
    public static final String COMPRESSED_CONTEXT = "compressedContext";
    public static final String ANSWER = "answer";
    public static final String DEPARTMENT = "department";
    public static final String TOOL_NAME = "toolName";
    public static final String TOOL_RESULT = "toolResult";
    public static final String TOOL_ARGUMENTS = "toolArguments";

    public static final Map<String, Channel<?>> SCHEMA = Map.ofEntries(
            entry(QUESTION, Channels.base(() -> "")),
            entry(DEPARTMENT, Channels.base(() -> "")),
            entry(REWRITTEN_QUERY, Channels.base(() -> "")),
            entry(RECALL_DOCS, Channels.base(ArrayList::new)),
            entry(RERANK_DOCS, Channels.base(ArrayList::new)),
            entry(COMPRESSED_CONTEXT, Channels.base(() -> "")),
            entry(TOOL_NAME, Channels.base(() -> "")),
            entry(TOOL_RESULT, Channels.base(() -> "")),
            entry(TOOL_ARGUMENTS, Channels.base(() -> new HashMap<String, Object>())),
            entry(ANSWER, Channels.base(() -> ""))
    );

    public RagState(Map<String, Object> map) {
        super(map);
    }

    @SuppressWarnings("unchecked")
    public List<Document> recallDocs() {
        return (List<Document>) value(RECALL_DOCS).orElse(List.of());
    }

    @SuppressWarnings("unchecked")
    public List<Document> rerankDocs() {
        return (List<Document>) value(RERANK_DOCS).orElse(List.of());
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> toolArguments() {
        return (Map<String, Object>) value(TOOL_ARGUMENTS).orElse(Map.of());
    }

    public String question() {
        return value(QUESTION).orElse("").toString();
    }

    public String answer() {
        return value(ANSWER).orElse("").toString();
    }

    public String department() {
        return value(DEPARTMENT).orElse("").toString();
    }

    public String rewrittenQuery() {
        return value(REWRITTEN_QUERY).orElse("").toString();
    }

    public String toolName() { return value(TOOL_NAME).orElse("").toString(); }

    public String toolResult() { return value(TOOL_RESULT).orElse("").toString(); }


}
