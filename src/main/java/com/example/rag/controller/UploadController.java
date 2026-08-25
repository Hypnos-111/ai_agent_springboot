package com.example.rag.controller;

import com.example.rag.dto.UploadResponse;
import com.example.rag.service.PdfIngestionService;

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
