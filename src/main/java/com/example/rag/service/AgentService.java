package com.example.rag.service;

import com.example.rag.state.AgentState;
import lombok.RequiredArgsConstructor;
import org.bsc.langgraph4j.CompiledGraph;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AgentService {
    
    private final CompiledGraph<AgentState> agentGraph;
    
    public String ask(String question) throws Exception {
        var result = agentGraph.invoke(Map.of(AgentState.QUESTION, question));
        return result.map(AgentState::answer).orElse("can not asnwer");
    }
}
