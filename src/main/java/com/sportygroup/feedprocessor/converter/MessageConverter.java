package com.sportygroup.feedprocessor.converter;

import com.sportygroup.feedprocessor.model.normalized.NormalizedMessage;

public interface MessageConverter<T> {
    NormalizedMessage convert(T message);
    boolean canConvert(Object message);
}
