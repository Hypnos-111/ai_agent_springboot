package com.example.rag.controller;

@RestController
@RequestMapping("/api/rag")
@RequiredArgsConstructor
public class RagController {

    private final AgentService agentService;

    @PostMapping("/ask")
    public String ask(@RequestBody RagRequest request) throws Exception {
        return agentService.ask(request.getQuestion());
    }

}
