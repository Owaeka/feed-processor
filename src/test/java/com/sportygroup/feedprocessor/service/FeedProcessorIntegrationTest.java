package com.sportygroup.feedprocessor.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedProcessorIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAlphaFeedEndToEnd() {
        String alphaMessage = """
                {
                    "msg_type": "odds_update",
                    "event_id": "ev123",
                    "values": {
                        "1": 2.0,
                        "X": 3.1,
                        "2": 3.8
                    }
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(alphaMessage, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/provider-alpha/feed", request, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Message processed successfully", response.getBody());
    }

    @Test
    void testBetaFeedEndToEnd() {
        String betaMessage = """
                {
                    "type": "SETTLEMENT",
                    "event_id": "ev456",
                    "result": "draw"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(betaMessage, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/provider-beta/feed", request, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Message processed successfully", response.getBody());
    }
}
