package com.example.rag.state;

import org.bsc.langgraph4j.state.Channel;
import org.bsc.langgraph4j.state.Channels;

import java.util.Map;

public class AgentState extends org.bsc.langgraph4j.state.AgentState {

    public static final String QUESTION = "question";
    public static final String ANSWER = "answer";
    public static final String ROUTE = "route";
    public static final String RESULT = "result";
    public static final Map<String, Channel<?>> SCHEMA = Map.of(
            QUESTION, Channels.base(() -> ""),
            ROUTE, Channels.base(() -> ""),
            RESULT, Channels.base(() -> ""),
            ANSWER, Channels.base(() -> "")
    );

    public AgentState(Map<String, Object> map) {
        super(map);
    }

    public String question() {
        return value(QUESTION).orElse("").toString();
    }

    public String answer() {
        return value(ANSWER).orElse("").toString();
    }

    public String route() {
        return value(ROUTE).orElse("").toString();
    }

    public String result() {
        return value(RESULT).orElse("").toString();
    }





}
