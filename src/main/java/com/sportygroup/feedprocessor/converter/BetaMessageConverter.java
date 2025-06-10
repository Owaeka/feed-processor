package com.sportygroup.feedprocessor.converter;

import com.sportygroup.feedprocessor.dto.provider.beta.BetaOdds;
import com.sportygroup.feedprocessor.dto.provider.beta.BetaSettlement;
import com.sportygroup.feedprocessor.exception.UnsupportedMessageException;
import com.sportygroup.feedprocessor.model.normalized.BetSettlement;
import com.sportygroup.feedprocessor.model.normalized.NormalizedMessage;
import com.sportygroup.feedprocessor.model.normalized.OddsChange;
import com.sportygroup.feedprocessor.model.normalized.Outcome;
import org.springframework.stereotype.Component;

@Component
public class BetaMessageConverter implements MessageConverter<Object>{
    @Override
    public NormalizedMessage convert(Object message) {
        if (message instanceof BetaOdds) {
            return convertOdds((BetaOdds) message);
        } else if (message instanceof BetaSettlement) {
            return convertSettlement((BetaSettlement) message);
        }
        throw new UnsupportedMessageException("Unsupported message type: " + message.getClass());
    }

    @Override
    public boolean canConvert(Object message) {
        return message instanceof BetaOdds || message instanceof BetaSettlement;
    }

    private OddsChange convertOdds(BetaOdds message) {
        return new OddsChange(
                message.getEventId(),
                message.getOdds().getHome(),
                message.getOdds().getDraw(),
                message.getOdds().getAway()
        );
    }

    private BetSettlement convertSettlement(BetaSettlement message) {
        Outcome outcome = switch (message.getResult()) {
            case "home" -> Outcome.HOME_WIN;
            case "draw" -> Outcome.DRAW;
            case "away" -> Outcome.AWAY_WIN;
            default -> throw new UnsupportedMessageException("Invalid result: " + message.getResult());
        };

        return new BetSettlement(message.getEventId(), outcome);
    }
}
