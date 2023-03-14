package it.academy.userservice.controllers;

import it.academy.userservice.core.exceptions.MultipleErrorResponse;
import it.academy.userservice.core.exceptions.SingleErrorResponse;
import it.academy.userservice.core.user.dtos.UserDTO;
import it.academy.userservice.core.user.dtos.UserLoginDTO;
import it.academy.userservice.core.user.dtos.UserRegistrationDTO;
import it.academy.userservice.services.api.IPersonalAccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/users")
public class PersonalAccountController {
    private final IPersonalAccountService service;

    public PersonalAccountController(IPersonalAccountService service) {
        this.service = service;
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDTO user) throws MultipleErrorResponse {
        service.register(user);
        return ResponseEntity.status(201).build();
    }

    @GetMapping(path = "/verification")
    public ResponseEntity<?> verified(@RequestParam(value = "code") String code,
                                      @RequestParam(value = "mail") String mail) throws SingleErrorResponse {
        service.verified(code, mail);
        return ResponseEntity.status(200).build();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO user) throws SingleErrorResponse {
        return ResponseEntity.status(200).body(service.login(user));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserDTO> get() throws SingleErrorResponse {
        return ResponseEntity.status(200).body(service.getMe());
    }
}