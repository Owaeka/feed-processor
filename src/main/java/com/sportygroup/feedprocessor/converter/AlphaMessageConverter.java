package com.sportygroup.feedprocessor.converter;

import com.sportygroup.feedprocessor.dto.provider.alpha.AlphaOddsUpdate;
import com.sportygroup.feedprocessor.dto.provider.alpha.AlphaSettlement;
import com.sportygroup.feedprocessor.exception.UnsupportedMessageException;
import com.sportygroup.feedprocessor.model.normalized.BetSettlement;
import com.sportygroup.feedprocessor.model.normalized.NormalizedMessage;
import com.sportygroup.feedprocessor.model.normalized.OddsChange;
import com.sportygroup.feedprocessor.model.normalized.Outcome;
import org.springframework.stereotype.Component;

@Component
public class AlphaMessageConverter implements MessageConverter<Object>{
    @Override
    public NormalizedMessage convert(Object message) {
        if (message instanceof AlphaOddsUpdate) {
            return convertOddsUpdate((AlphaOddsUpdate) message);
        } else if (message instanceof AlphaSettlement) {
            return convertSettlement((AlphaSettlement) message);
        }
        throw new UnsupportedMessageException("Unsupported message type: " + message.getClass());
    }



    @Override
    public boolean canConvert(Object message) {
        return message instanceof AlphaOddsUpdate || message instanceof AlphaSettlement;
    }

    private OddsChange convertOddsUpdate(AlphaOddsUpdate message) {
        return new OddsChange(
                message.getEventId(),
                message.getValues().get("1"),
                message.getValues().get("X"),
                message.getValues().get("2")
        );
    }

    private BetSettlement convertSettlement(AlphaSettlement message) {
        Outcome outcome = switch (message.getOutcome()) {
            case "1" -> Outcome.HOME_WIN;
            case "X" -> Outcome.DRAW;
            case "2" -> Outcome.AWAY_WIN;
            default -> throw new UnsupportedMessageException("Invalid outcome: " + message.getOutcome());
        };

        return new BetSettlement(message.getEventId(), outcome);
    }
}
