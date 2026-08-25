package com.example.rag.service;

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import org.springframework.stereotype.Component;

@Component
public class OnnxTokenizer {

    private final HuggingFaceTokenizer tokenizer;

    public OnnxTokenizer() throws Exception {
        this.tokenizer= HuggingFaceTokenizer.newInstance("models/bge-reranker-v2-m3/tokenizer.json");
    }

    public TokenResult encode(String query,String document) {
        String textPair = document;
        long[] inputIds = tokenizer.encode(query, textPair).getIds();
        long[] attentionMask = new long[inputIds.length];
        for (int i = 0; i < attentionMask.length; i++) {
            attentionMask[i] = 1L;
        }
        return new TokenResult(inputIds, attentionMask);
    }

}
