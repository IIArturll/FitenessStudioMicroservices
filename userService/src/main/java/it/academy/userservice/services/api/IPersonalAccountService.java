package it.academy.userservice.services.api;


import it.academy.userservice.core.user.dtos.UserDTO;
import it.academy.userservice.core.user.dtos.UserLoginDTO;
import it.academy.userservice.core.user.dtos.UserRegistrationDTO;

public interface IPersonalAccountService {

    void register(UserRegistrationDTO user);

    void verified(String code, String mail);

    String login(UserLoginDTO user);

    UserDTO getMe();
}
