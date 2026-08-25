package com.example.rag.service;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import com.example.rag.model.RankedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BgeRerankerService {
    
    private static final int TOP_N = 5;
    private final OrtEnvironment env;
    private final OrtSession session;
    private final OnnxTokenizer tokenizer;

    public List<Document> rerank(String question, List<Document> docs)  {
        if (docs.isEmpty()) {
            return List.of();
        }

        List<Float> scores = batchScore(question, docs);
        return finalRerank(docs, scores);
    }

    private List<Float> batchScore(String query, List<Document> docs) {

        try {
            List<TokenResult> tokens = new ArrayList<>(docs.size());
            int maxLength = 0;
            for (Document doc : docs) {
                TokenResult token= tokenizer.encode(query, doc.getText());
                tokens.add(token);
                maxLength = Math.max(maxLength, token.inputIds().length);
            }

            long[][] inputIds = new long[docs.size()][maxLength];
            long[][] attentionMask = new long[docs.size()][maxLength];

            for(int i = 0; i < tokens.size(); i++) {
                TokenResult token = tokens.get(i);
                System.arraycopy(token.inputIds(), 0, inputIds[i], 0, token.inputIds().length);
                System.arraycopy(token.attentionMask(), 0, attentionMask[i], 0, token.attentionMask().length);
            }

            try (
                OnnxTensor inputIdsTensor = OnnxTensor.createTensor(env, inputIds);
                OnnxTensor attentionMaskTensor = OnnxTensor.createTensor(env, attentionMask);
            ) {
              var inputs = Map.of("input_ids", inputIdsTensor, "attention_mask", attentionMaskTensor);

              try (OrtSession.Result result = session.run(inputs)) {
                  float[][] logits = (float[][]) result.get(0).getValue();
                  List<Float> scores = new ArrayList<>(logits.length);
                  for(float[] row : logits) {
                      scores.add(row[0]);
                  }
                  return scores;
              }
            }
        } catch (Exception e) {
            throw new RuntimeException("Batch rerank failed", e);
        }
    }

    public List<Document> finalRerank(List<Document> docs, List<Float> scores) {
        PriorityQueue<RankedDocument> heap = new PriorityQueue<>(Comparator.comparing(RankedDocument::score));
        for (int i = 0; i < docs.size(); i++) {
            heap.offer(new RankedDocument(docs.get(i), scores.get(i)));
            if(heap.size() > TOP_N) {
                heap.poll();
            }
        }
        List<RankedDocument> result = new ArrayList<>(heap);
        result.sort(Comparator.comparing(RankedDocument::score).reversed());
        return result.stream().map(RankedDocument::document).toList();
    }

}
