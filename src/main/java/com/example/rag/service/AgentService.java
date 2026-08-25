package com.example.rag.service;

import com.example.rag.RagApplication;

@Service
@RequiredArgsConstructor
public class AgentService {
    
    private final CompiledGraph<AgentState> agentGraph;
    
    public String ask(String question) throws Exception {
        var result = agentGraph.invoke(Map.of(AgentState.QUESTION, question));
        return result.map(AgentState::answer).orElse("can not asnwer");
    }
}
