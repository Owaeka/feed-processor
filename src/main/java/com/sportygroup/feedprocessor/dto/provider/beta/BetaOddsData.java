package com.sportygroup.feedprocessor.dto.provider.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BetaOddsData {
    @JsonProperty("home")
    private BigDecimal home;

    @JsonProperty("draw")
    private BigDecimal draw;

    @JsonProperty("away")
    private BigDecimal away;
}
