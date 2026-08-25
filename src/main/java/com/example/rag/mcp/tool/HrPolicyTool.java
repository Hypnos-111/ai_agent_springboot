package com.example.rag.mcp.tool;

import com.example.rag.service.EmployeeService;
import com.example.rag.state.RagState;

import java.util.Map;

@Component("search_hr_policy")
@RequiredArgsConstrutor
public class HrPolicyTool implements ToolExecutor {

    private final CompiledGraph<RagState> ragGraph;

    @Override
    public String execute(Map<String, Object> args) throws Exception {
        String question = (String) args.get("question");
        var result = ragGraph.invoke(Map.of(RagState.QUESTION, question, RagState.DEPARTMENT, "HR"));

        return result.map(RagState::answer).orElse("not found");
    }
}
