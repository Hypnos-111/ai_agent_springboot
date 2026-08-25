package com.example.rag.mcp.tool;

import com.example.rag.mcp.ToolExecutor;
import com.example.rag.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("get_employee_leave")
@RequiredArgsConstructor
public class LeaveTool implements ToolExecutor {

    private final LeaveService leaveService;

    @Override
    public String execute(Map<String, Object> args) throws Exception {
        String question = (String) args.get("question");
        return leaveService.getLeave(question);
    }
}
