package it.academy.userservice.services.api;

import it.academy.userservice.core.user.dtos.UserCreateDTO;
import it.academy.userservice.core.user.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.UUID;

public interface IUserService {
    UUID create(UserCreateDTO user);

    UserDTO get(UUID uuid);

    UUID update(UUID uuid, Instant dtUpdate, UserCreateDTO user);

    Page<UserDTO> getPage(Pageable pageable);

    UserDetails getUserDetails(String mail);
}
