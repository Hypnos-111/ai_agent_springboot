package com.example.rag.controller;

import com.example.rag.dto.UploadResponse;
import com.example.rag.service.PdfIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final PdfIngestionService pdfService;

    @PostMapping(value = "/pdf", consumes = "multipart/form-data")
    public ResponseEntity<UploadResponse> uploadPdf(@RequestPart("file") MultipartFile file)  {
        try {
            pdfService.ingest(file);
            return ResponseEntity.ok(new UploadResponse(file.getOriginalFilename(), "uploaded Success"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new UploadResponse(file.getOriginalFilename(), e.getMessage()));
        }
    }

}
