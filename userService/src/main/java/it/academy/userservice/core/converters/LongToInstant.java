package it.academy.userservice.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;

public class LongToInstant extends StdConverter<Long, Instant> {
    @Override
    public Instant convert(Long value) {
        return Instant.ofEpochMilli(value);
    }
}
