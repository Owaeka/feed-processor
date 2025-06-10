package com.sportygroup.feedprocessor.model.normalized;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OddsChange extends NormalizedMessage {
    private BigDecimal homeWinOdds;
    private BigDecimal drawOdds;
    private BigDecimal awayWinOdds;

    public OddsChange() {
        super("ODDS_CHANGE");
    }

    public OddsChange(String eventId, BigDecimal homeWinOdds, BigDecimal drawOdds, BigDecimal awayWinOdds) {
        super(eventId, "ODDS_CHANGE");
        this.homeWinOdds = homeWinOdds;
        this.drawOdds = drawOdds;
        this.awayWinOdds = awayWinOdds;
    }
}
