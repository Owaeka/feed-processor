package com.sportygroup.feedprocessor.model.normalized;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class NormalizedMessage {
    private String eventId;
    private Instant timestamp;
    private String messageType;

    public NormalizedMessage() {
        this.timestamp = Instant.now();
    }

    public NormalizedMessage(String messageType) {
        this.messageType  = messageType;
        this.timestamp = Instant.now();
    }

    public NormalizedMessage(String eventId, String messageType) {
        this.eventId = eventId;
        this.messageType  = messageType;
        this.timestamp = Instant.now();
    }
}
