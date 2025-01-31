package fhtechnikum.at.swkom_paperless_groupf.apps.controller;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import fhtechnikum.at.swkom_paperless_groupf.apps.service.RabbitMQService;
import fhtechnikum.at.swkom_paperless_groupf.apps.service.DocService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/docs")
@CrossOrigin
@Slf4j
public class DocController {

    @Autowired
    DocService docService;

    @Autowired
    private RabbitMQService rabbitMQService;

    @Operation(summary = "Get all documents", description = "Fetches a list of all available documents.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documents retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doc.class))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Doc>> getDocs() {
        log.info("Fetching all documents...");
        List<Doc> docs = docService.findAll();
        log.debug("Fetched {} documents.", docs.size());
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }

    @Operation(summary = "Upload a document", description = "Uploads a document to the server.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document uploaded successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doc.class))),
            @ApiResponse(responseCode = "400", description = "Invalid file upload (e.g., empty file)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error during file upload", content = @Content)
    })
    @PostMapping("/upload")
    public ResponseEntity<Doc> uploadDoc(
            @RequestParam("file") MultipartFile file) throws Exception {
        log.info("Received a file upload request: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            log.warn("File upload failed: Empty file.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Doc savedDoc = docService.saveDocument(file);
            rabbitMQService.sendMessageToOCR(savedDoc.getTitle(), savedDoc.getId().toString());
            log.info("File successfully saved: {}", savedDoc.getId());
            return new ResponseEntity<>(savedDoc, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while saving file: {}", file.getOriginalFilename(), e);
            throw e;
        }
    }

    @Operation(summary = "Searches documents by query")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documents retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Doc.class))),
            @ApiResponse(responseCode = "400", description = "Invalid query parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Doc>> searchDocuments(@RequestParam String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                log.error("Invalid query parameter: query cannot be null or empty");
                return ResponseEntity.badRequest().build();
            }

            log.info("Searching documents with query: {}", query);
            List<Doc> results = docService.searchDocuments(query);
            return ResponseEntity.ok(results);

        } catch (Exception e) {
            log.error("Error occurred while searching documents: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Fetches a document by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document retrieved successfully", content = @Content(schema = @Schema(implementation = Doc.class))),
            @ApiResponse(responseCode = "404", description = "Document not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/download")
    public ResponseEntity<byte[]> getDocumentById(@RequestParam String id, String title) {
        try {
            byte[] pdfFile = docService.getFile(id, title);
            Doc document = docService.getDocById(Long.parseLong(id));
            if (document == null || pdfFile == null) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", document.getTitle());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfFile);
        } catch (IllegalArgumentException e) {
            log.error("Document not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving document with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

}
