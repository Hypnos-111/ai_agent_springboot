package com.example.rag.config;

@Configuration
public class OpenAiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

}
