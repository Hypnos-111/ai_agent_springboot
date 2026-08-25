package com.example.rag.config;

import com.example.rag.mcp.client.EmployeeMcpClient;
import com.example.rag.mcp.client.HrMcpClient;
import com.example.rag.mcp.client.JiraMcpClient;
import com.example.rag.mcp.client.LeaveMcpClient;
import com.example.rag.service.EmployeeService;
import com.example.rag.service.JiraService;
import com.example.rag.service.LeaveService;
import com.example.rag.state.AgentState;
import com.example.rag.state.RagState;
import lombok.RequiredArgsConstructor;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.StateGraph;
import org.bsc.langgraph4j.action.AsyncNodeActionWithConfig;
import org.bsc.langgraph4j.action.Command;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static org.bsc.langgraph4j.action.AsyncNodeActionWithConfig.node_async;

@Configuration
@RequiredArgsConstructor
public class AgentGraphConfig {
    private final ChatClient chatClient;
    private final LeaveService leaveService;
    private final EmployeeService employeeService;
    private final JiraService jiraService;
    private final CompiledGraph<RagState> ragGraph;
    private final HrMcpClient hrMcpClient;
    private final LeaveMcpClient leaveMcpClient;
    private final EmployeeMcpClient employeeMcpClient;
    private final JiraMcpClient jiraMcpClient;

    @Bean
    public CompiledGraph<AgentState> agentGraph() throws Exception {
        StateGraph<AgentState> graph = new StateGraph<>(AgentState.SCHEMA, AgentState::new);

        AsyncNodeActionWithConfig<AgentState> plannerAction = node_async((state, config) -> {
            String route = chatClient.prompt("""
                    only input
                    hr
                    leave
                    employee
                    jira
                    """).call().content().trim().toLowerCase();

            Set<String> routes = Set.of("hr", "leave", "employee", "jira");
            if (!routes.contains(route)) {
                route = "hr";
            }
            return Map.of(AgentState.ROUTE, route);
        });
        graph.addNode("planner", plannerAction);

        AsyncNodeActionWithConfig<AgentState> hrNode = node_async((state, config) -> {
            String answer = hrMcpClient.searchHrPolicy(state.question());
            return Map.of(AgentState.ANSWER, answer);
        });
        graph.addNode("hrNode", hrNode);

        AsyncNodeActionWithConfig<AgentState> leaveNode = node_async((state, config) -> {
            String answer = leaveMcpClient.getLeave(state.question());
            return Map.of(AgentState.ANSWER, answer);
        });
        graph.addNode("leaveNode", leaveNode);

        AsyncNodeActionWithConfig<AgentState> employeeNode = node_async((state, config) -> {
            String answer = employeeMcpClient.getInfo(state.question());
            return Map.of(AgentState.ANSWER, answer);
        });
        graph.addNode("employeeNode", employeeNode);

        AsyncNodeActionWithConfig<AgentState> jiraNode = node_async((state, config) -> {
            String answer = jiraService.search(state.question());
            return Map.of(AgentState.ANSWER, answer);
        });
        graph.addNode("jiraNode", jiraNode);

        graph.addEdge(StateGraph.START, "planner");
        graph.addConditionalEdges("planner",
                (state, config) -> CompletableFuture.completedFuture(new Command(state.route(), Map.of())),
                Map.of("hr", "hrNode", "leave", "leaveNode", "employee", "employeeNode", "jira",
                  "jiraNode"));

        graph.addEdge("hrNode", StateGraph.END);
        graph.addEdge("leaveNode", StateGraph.END);
        graph.addEdge("employeeNode", StateGraph.END);
        graph.addEdge("jiraNode", StateGraph.END);

        return graph.compile();
    }
}
