package com.sportygroup.feedprocessor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.feedprocessor.converter.AlphaMessageConverter;
import com.sportygroup.feedprocessor.converter.BetaMessageConverter;
import com.sportygroup.feedprocessor.converter.MessageConverter;
import com.sportygroup.feedprocessor.model.normalized.BetSettlement;
import com.sportygroup.feedprocessor.model.normalized.OddsChange;
import com.sportygroup.feedprocessor.model.normalized.Outcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageProcessorServiceTest {
    @Mock
    private MessageQueueService messageQueueService;

    private MessageProcessorService messageProcessorService;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<MessageConverter<Object>> converters = List.of(
                new AlphaMessageConverter(),
                new BetaMessageConverter()
        );
        messageProcessorService = new MessageProcessorService(converters, messageQueueService, objectMapper);
    }

    @Test
    void testProcessAlphaOddsUpdate() {
        String alphaOddsMessage = """
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

        messageProcessorService.processAlphaMessage(alphaOddsMessage);

        ArgumentCaptor<OddsChange> captor = ArgumentCaptor.forClass(OddsChange.class);

        verify(messageQueueService).sendToQueue(captor.capture());
        OddsChange capturedMessage = captor.getValue();

        assertEquals("ev123", capturedMessage.getEventId());
        assertEquals("ODDS_CHANGE", capturedMessage.getMessageType());
        assertEquals(new BigDecimal("2.0"), capturedMessage.getHomeWinOdds());
        assertEquals(new BigDecimal("3.1"), capturedMessage.getDrawOdds());
        assertEquals(new BigDecimal("3.8"), capturedMessage.getAwayWinOdds());
        assertNotNull(capturedMessage.getTimestamp());
    }

    @Test
    void testProcessAlphaSettlement() {
        String alphaSettlementMessage = """
            {
                "msg_type": "settlement",
                "event_id": "ev123",
                "outcome": "1"
            }
            """;

        messageProcessorService.processAlphaMessage(alphaSettlementMessage);

        ArgumentCaptor<BetSettlement> captor = ArgumentCaptor.forClass(BetSettlement.class);
        verify(messageQueueService).sendToQueue(captor.capture());
        BetSettlement capturedMessage = captor.getValue();

        assertEquals("ev123", capturedMessage.getEventId());
        assertEquals("BET_SETTLEMENT", capturedMessage.getMessageType());
        assertEquals(Outcome.HOME_WIN, capturedMessage.getOutcome());
        assertNotNull(capturedMessage.getTimestamp());
    }

    @Test
    void testProcessBetaOdds() {
        String betaOddsMessage = """
            {
                "type": "ODDS",
                "event_id": "ev456",
                "odds": {
                    "home": 1.95,
                    "draw": 3.2,
                    "away": 4.0
                }
            }
            """;

        messageProcessorService.processBetaMessage(betaOddsMessage);

        ArgumentCaptor<OddsChange> captor = ArgumentCaptor.forClass(OddsChange.class);
        verify(messageQueueService).sendToQueue(captor.capture());

        OddsChange capturedMessage = captor.getValue();
        assertEquals("ev456", capturedMessage.getEventId());
        assertEquals("ODDS_CHANGE", capturedMessage.getMessageType());
        assertEquals(new BigDecimal("1.95"), capturedMessage.getHomeWinOdds());
        assertEquals(new BigDecimal("3.2"), capturedMessage.getDrawOdds());
        assertEquals(new BigDecimal("4.0"), capturedMessage.getAwayWinOdds());
        assertNotNull(capturedMessage.getTimestamp());
    }

    @Test
    void testProcessBetaSettlement() {
        String betaSettlementMessage = """
            {
                "type": "SETTLEMENT",
                "event_id": "ev456",
                "result": "away"
            }
            """;

        messageProcessorService.processBetaMessage(betaSettlementMessage);

        ArgumentCaptor<BetSettlement> captor = ArgumentCaptor.forClass(BetSettlement.class);
        verify(messageQueueService).sendToQueue(captor.capture());

        BetSettlement capturedMessage = captor.getValue();
        assertEquals("ev456", capturedMessage.getEventId());
        assertEquals("BET_SETTLEMENT", capturedMessage.getMessageType());
        assertEquals(Outcome.AWAY_WIN, capturedMessage.getOutcome());
        assertNotNull(capturedMessage.getTimestamp());
    }

    @Test
    void testProcessAlphaInvalidMessageType() {
        String invalidMessage = """
            {
                "msg_type": "invalid_type",
                "event_id": "ev123"
            }
            """;

        assertThrows(RuntimeException.class, () ->
                messageProcessorService.processAlphaMessage(invalidMessage));
    }

    @Test
    void testProcessBetaInvalidMessageType() {
        String invalidMessage = """
            {
                "type": "INVALID_TYPE",
                "event_id": "ev456"
            }
            """;

        assertThrows(RuntimeException.class, () ->
                messageProcessorService.processBetaMessage(invalidMessage));
    }
}