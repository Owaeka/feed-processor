package com.sportygroup.feedprocessor.model.normalized;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BetSettlement extends NormalizedMessage {
    private Outcome outcome;

    public BetSettlement() {
        super("BET_SETTLEMENT");
    }

    public BetSettlement(String eventId, Outcome outcome) {
        super(eventId, "BET_SETTLEMENT");
        this.outcome = outcome;
    }
}
