package com.example.rag.config;

import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnnxConfig {

    @Bean
    public OrtEnvironment ortEnvironment() {
        return OrtEnvironment.getEnvironment();
    }

    @Bean
    public OrtSession rerankerSession(OrtEnvironment env) throws Exception {
        return env.createSession("models/bge-reranker-v2-m3/model.onnx", new OrtSession.SessionOptions());
    }
}
