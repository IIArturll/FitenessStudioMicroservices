package com.example.mailservice.core.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"logref", "message"})
@JsonIgnoreProperties(value = {"cause", "localizedMessage", "stackTrace","suppressed"})
public class SingleErrorResponse extends RuntimeException {
    @JsonProperty(value = "logref")
    private String logref;
    @JsonProperty("message")
    private String message;

    public SingleErrorResponse(String logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public String getMessage() {
        return message;
    }
}
