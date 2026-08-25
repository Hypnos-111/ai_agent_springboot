package com.example.rag.mcp.tool;

import com.example.rag.mcp.ToolExecutor;
import com.example.rag.service.JiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("jira_search")
@RequiredArgsConstructor
public class JiraTool implements ToolExecutor {

    private final JiraService jiraService;

    @Override
    public String execute(Map<String, Object> args) throws Exception {
        String question = (String) args.get("question");
        return jiraService.search(question);
    }
}
