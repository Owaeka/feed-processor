package com.sportygroup.feedprocessor.dto.provider.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BetaSettlement {
    @JsonProperty("type")
    private String type;

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("result")
    private String result;
}
