package com.sportygroup.feedprocessor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.feedprocessor.model.normalized.NormalizedMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageQueueService {
    private static final Logger logger = LoggerFactory.getLogger(MessageQueueService.class);
    private final ObjectMapper objectMapper;

    //Totally mocked class, just for logging
    public void sendToQueue(NormalizedMessage message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            logger.info("Sending message to queue: {}", jsonMessage);

        } catch (Exception e) {
            logger.error("Error sending message to queue", e);
            throw new RuntimeException("Failed to send message to queue", e);
        }
    }
}
