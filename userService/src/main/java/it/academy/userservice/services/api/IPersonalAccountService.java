package it.academy.userservice.services.api;


import it.academy.userservice.core.user.dtos.UserDTO;
import it.academy.userservice.core.user.dtos.UserLoginDTO;
import it.academy.userservice.core.user.dtos.UserRegistrationDTO;

import java.util.UUID;

public interface IPersonalAccountService {

    UUID register(UserRegistrationDTO user);

    UUID verified(String code, String mail);

    String login(UserLoginDTO user);

    UserDTO getMe();
}
