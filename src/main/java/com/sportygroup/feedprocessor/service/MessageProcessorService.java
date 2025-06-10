package com.sportygroup.feedprocessor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.feedprocessor.converter.MessageConverter;
import com.sportygroup.feedprocessor.dto.provider.alpha.AlphaOddsUpdate;
import com.sportygroup.feedprocessor.dto.provider.alpha.AlphaSettlement;
import com.sportygroup.feedprocessor.dto.provider.beta.BetaOdds;
import com.sportygroup.feedprocessor.dto.provider.beta.BetaSettlement;
import com.sportygroup.feedprocessor.exception.UnsupportedMessageException;
import com.sportygroup.feedprocessor.model.normalized.NormalizedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageProcessorService {
    private final List<MessageConverter<Object>> converters;
    private final MessageQueueService messageQueueService;
    private final ObjectMapper objectMapper;

    public void processAlphaMessage(String rawMessage) {
        try {
            JsonNode jsonNode = objectMapper.readTree(rawMessage);
            String msgType = jsonNode.get("msg_type").asText().trim().toLowerCase();

            Object message = switch (msgType) {
                case "odds_update" -> objectMapper.treeToValue(jsonNode, AlphaOddsUpdate.class);
                case "settlement" -> objectMapper.treeToValue(jsonNode, AlphaSettlement.class);
                default -> throw new UnsupportedMessageException("Unknown message type: " + msgType);
            };

            processMessage(message);
        } catch (Exception e) {
            throw new UnsupportedMessageException("Failed to process Alpha message", e);
        }
    }

    public void processBetaMessage(String rawMessage) {
        try {
            JsonNode jsonNode = objectMapper.readTree(rawMessage);
            String type = jsonNode.get("type").asText().trim().toUpperCase();
            System.out.println("Type: " + type);
            Object message = switch (type) {
                case "ODDS" -> objectMapper.treeToValue(jsonNode, BetaOdds.class);
                case "SETTLEMENT" -> objectMapper.treeToValue(jsonNode, BetaSettlement.class);
                default -> throw new UnsupportedMessageException("Unknown message type: " + type);
            };

            processMessage(message);
        } catch (Exception e) {
            throw new UnsupportedMessageException("Failed to process Beta message", e);
        }
    }

    private void processMessage(Object message) {
        MessageConverter<Object> converter = converters.stream()
                .filter(c -> c.canConvert(message))
                .findFirst()
                .orElseThrow(() -> new UnsupportedMessageException("No converter found for message type: " + message.getClass()));

        NormalizedMessage standardMessage = converter.convert(message);
        messageQueueService.sendToQueue(standardMessage);
    }
}
