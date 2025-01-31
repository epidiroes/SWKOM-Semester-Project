package fhtechnikum.at.ocrworker.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class ElasticsearchServiceOCR {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public void indexDocument(String id, String filename, String ocrText) throws IOException {
        // Create the document content (JSON format)
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("documentId", id);
        jsonMap.put("filename", filename);
        jsonMap.put("ocrText", ocrText);
        jsonMap.put("@timestamp", Instant.now().toString()); // Add the @timestamp field

        // Create an index request
        IndexRequest<Map<String, Object>> request = IndexRequest.of(i -> i
                .index("documents")
                .id(id)
                .document(jsonMap)
        );

        // Send the index request to Elasticsearch
        elasticsearchClient.index(request);

    }
}
