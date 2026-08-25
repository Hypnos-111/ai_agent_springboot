package com.example.rag.mcp.tool;

import com.example.rag.service.JiraService;
import com.example.rag.service.LeaveService;

import java.util.Map;

@Component("get_employee_leave")
@RequiredArgsConstrutor
public class LeaveTool implements ToolExecutor {

    private final LeaveService leaveService;

    @Override
    public String execute(Map<String, Object> args) throws Exception {
        String question = (String) args.get("question");
        return leaveService.getLeave(question);
    }
}
