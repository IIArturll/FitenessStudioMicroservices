package it.academy.userservice.services.api;

import it.academy.userservice.core.exceptions.SingleErrorResponse;
import it.academy.userservice.core.user.dtos.UserCreateDTO;
import it.academy.userservice.core.user.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.UUID;

public interface IUserService {
    void create(UserCreateDTO user);

    UserDTO get(UUID uuid) throws SingleErrorResponse;

    void update(UUID uuid, Instant dtUpdate, UserCreateDTO user) throws SingleErrorResponse;

    Page<UserDTO> getPage(Pageable pageable);

    UserDetails getUserDetails(String mail);
}
