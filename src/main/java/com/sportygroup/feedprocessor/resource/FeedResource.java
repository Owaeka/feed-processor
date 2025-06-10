package com.sportygroup.feedprocessor.resource;

import com.sportygroup.feedprocessor.dto.ErrorResponse;
import com.sportygroup.feedprocessor.service.MessageProcessorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedResource {
    private static final Logger logger = LoggerFactory.getLogger(FeedResource.class);
    private final MessageProcessorService messageProcessorService;

    @PostMapping("/provider-alpha/feed")
    public ResponseEntity<Void> handleAlphaFeed(@RequestBody String rawMessage) {
        logger.info("Received Alpha message: {}", rawMessage);

        messageProcessorService.processAlphaMessage(rawMessage);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/provider-beta/feed")
    public ResponseEntity<Void> handleBetaFeed(@RequestBody String rawMessage) {
        logger.info("Received Beta message: {}", rawMessage);

        messageProcessorService.processBetaMessage(rawMessage);

        return ResponseEntity.ok().build();
    }
}
