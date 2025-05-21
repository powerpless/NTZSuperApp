package org.example.ntzsuperapp.Controllers;

import org.example.ntzsuperapp.DTO.FileDTO;
import org.example.ntzsuperapp.Entity.FileDescriptor;
import org.example.ntzsuperapp.Services.FileDescriptorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileDescriptorService fileService;

    public FileController(FileDescriptorService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") FileDTO file) {
        try {
            FileDescriptor saved = fileService.saveFile(file);
            return ResponseEntity.ok("File uploaded successfully with ID: " + saved.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        return fileService.getFile(id)
                .map(file -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                        .contentType(MediaType.parseMediaType(file.getType()))
                        .body(file.getBytes()))
                .orElse(ResponseEntity.notFound().build());
    }
}
