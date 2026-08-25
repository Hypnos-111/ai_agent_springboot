package com.example.rag.controller;

import com.example.rag.dto.RagRequest;
import com.example.rag.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
