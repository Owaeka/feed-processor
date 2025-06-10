package com.sportygroup.feedprocessor.dto.provider.alpha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class AlphaOddsUpdate {
    @JsonProperty("msg_type")
    private String msgType;

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("values")
    private Map<String, BigDecimal> values;
}
