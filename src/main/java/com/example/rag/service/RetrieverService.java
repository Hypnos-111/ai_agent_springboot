package com.example.rag.service;

@Service
@RequiredArgsConstructor
public class RetrieverService {

    private final VectorStore vectorStore;

    public List<Document> retrieve(String query) {
        return vectorStore.similaritySeaarch(
                SearchRequest.builder().query(query)
                        .topK(30).similarityThreshold(0.75).build()
        );
    }

    public List<Document> retrieve1(String query) {
        SearchRequest request = SearchRequest.builder().query(query)
                .filterExpression("""
                    department == 'HR'
                     && type == 'FAQ' 
                     """).topK(30).similarityThreshold(0.75).build();
        return vectorStore.similaritySeaarch(request);
    }

}
