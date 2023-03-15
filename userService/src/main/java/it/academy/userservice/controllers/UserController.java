package it.academy.userservice.controllers;

import it.academy.userservice.core.exceptions.SingleErrorResponse;
import it.academy.userservice.core.user.dtos.UserCreateDTO;
import it.academy.userservice.core.user.dtos.UserDTO;
import it.academy.userservice.security.MyUserDetailsService;
import it.academy.userservice.services.api.IUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController()
@RequestMapping("/users")
public class UserController {
    private final IUserService service;
    private final MyUserDetailsService userDetailsService;

    public UserController(IUserService service,
                          MyUserDetailsService userDetailsService) {
        this.service = service;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserCreateDTO user) {
        service.create(user);
        return ResponseEntity.status(201).build();
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<UserDTO> get(@PathVariable("uuid") UUID uuid) throws SingleErrorResponse {
        return ResponseEntity.status(200).body(service.get(uuid));
    }

    @PutMapping(path = "/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@PathVariable("uuid") UUID uuid,
                                    @PathVariable("dt_update") Instant dtUpdate,
                                    @Valid @RequestBody UserCreateDTO user) throws SingleErrorResponse {
        service.update(uuid, dtUpdate, user);
        return ResponseEntity.status(200).build();
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getPage(Pageable pageable) {
        return ResponseEntity.status(200).body(service.getPage(pageable));
    }

    @GetMapping("/userDetails/mail/{mail}")
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable("mail") String mail) {
        return ResponseEntity.status(200).body(service.getUserDetails(mail));
    }
}
