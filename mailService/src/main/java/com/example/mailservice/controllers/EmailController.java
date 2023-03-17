package com.example.mailservice.controllers;

import com.example.mailservice.services.api.ISenderService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class EmailController {

    private final ISenderService senderService;

    public EmailController(ISenderService senderService) {
        this.senderService = senderService;
    }

    @PostMapping(value = "/send")
    public ResponseEntity<?> sendSimpleEmail(@NotBlank @Email @RequestParam("email") String email) {
        senderService.send(email);
        return ResponseEntity.status(200).build();
    }

    @GetMapping(value = "/verification")
    public ResponseEntity<Boolean> verify(@NotBlank @Email @RequestParam("email") String email,
                                          @NotBlank @RequestParam("code") String code) {
        return ResponseEntity.status(200).body(senderService.verify(email, code));
    }
}