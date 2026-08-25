package com.example.rag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfIngestionService {

    private final VectorStore vectorStore;

    public void ingest(MultipartFile file) throws Exception {
        Path temp = Files.createTempFile("pdf", ".pdf");
        try {
            file.transferTo(temp.toFile());
            PagePdfDocumentReader reader = new PagePdfDocumentReader(new FileSystemResource(temp));

            List<Document> docs = reader.get();
            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(800)
                    .withMinChunkSizeChars(200)
                    .withMinChunkLengthToEmbed(5)
                    .build();
            List<Document> chunks = splitter.apply(docs);
            chunks.forEach(doc -> doc.getMetadata().put("filename", file.getOriginalFilename()));
            vectorStore.add(chunks);
        }
        finally {
            Files.deleteIfExists(temp);
        }
    }

}
