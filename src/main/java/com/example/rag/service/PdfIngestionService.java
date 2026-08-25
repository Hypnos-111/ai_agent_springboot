package com.example.rag.service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class PdfIngestionService {

    private final VectorStore vectorStore;

    public void ingest(MultipartFile file) throws Exception {
        Path temp = Files.createTempFile("pdf", ".pdf");
        file.transferTo(temp.toFile());
        PagePdfDocumentReader reader = new PagePdfDocumentReader(temp.toString());

        List<Document> docs = reader.get();
        TokenTextSplitter splitter = TokenTextSplitter.builder
                .withChunkSize(800)
                .withMinChunkSizeChars(200)
                .withMinChunkLengthToEmbed(5).build();
        List<Document> chunks = splitter.apply(docs);
        chunks.forEach(doc -> {
                doc,getMetadata().put("filename", file.getOriginalFilename());
        });
        ventorStore.add(chunks);
    }

}
