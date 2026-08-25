package com.example.rag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class JiraService {

    private final RestClient restClient;

    public String search(String question) {

        return restClient.get()
                .uri("/rest/api/3/search?jql=project=HR")
                .retrieve()
                .body(String.class);
    }
}
