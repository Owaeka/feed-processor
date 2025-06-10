package com.sportygroup.feedprocessor.dto.provider.alpha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AlphaSettlement {
    @JsonProperty("msg_type")
    private String msgType;

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("outcome")
    private String outcome;
}
