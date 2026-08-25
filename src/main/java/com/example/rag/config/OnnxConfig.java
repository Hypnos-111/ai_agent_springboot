package com.example.rag.config;

import java.beans.JavaBean;

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
