package com.sportygroup.feedprocessor.dto.provider.alpha;

public enum AlphaMsgType {
    ODDS_UPDATE("odds_update"),
    SETTLEMENT("settlement");

    private final String value;

    AlphaMsgType(String value) {
        this.value = value;
    }


}
