package com.example.rag.mcp.tool;

import com.example.rag.service.JiraService;

import java.util.Map;

@Component("jira_search")
@RequiredArgsConstrutor
public class JiraTool implements ToolExecutor {

    private final JiraService jiraService;

    @Override
    public String execute(Map<String, Object> args) throws Exception {
        String question = (String) args.get("question");
        return jiraService.search(question);
    }
}
