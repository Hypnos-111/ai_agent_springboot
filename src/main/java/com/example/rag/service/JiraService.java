package com.example.rag.service;

@Service
@RequiredArgsConstructor
public class JiraService {

    private final WebClient webClient;

    public String search(String question) {

        return webClient.get()
                .uri("/rest/api/3/search?jql=project=HR")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
