package it.academy.productservice.config.web;


import org.springframework.core.convert.converter.Converter;

import java.time.Instant;

public class StrToInstantConverter implements Converter<String, Instant> {
    @Override
    public Instant convert(String source) {
        return Instant.ofEpochMilli(Long.parseLong(source));
    }
}
