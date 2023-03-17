package com.example.mailservice.services.api;

public interface ISenderService {
    void send(String recipient);
    boolean verify(String recipient, String code);
}
