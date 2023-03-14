package it.academy.userservice.services.api;


import it.academy.userservice.core.exceptions.MultipleErrorResponse;
import it.academy.userservice.core.exceptions.SingleErrorResponse;
import it.academy.userservice.core.user.dtos.UserDTO;
import it.academy.userservice.core.user.dtos.UserLoginDTO;
import it.academy.userservice.core.user.dtos.UserRegistrationDTO;

public interface IPersonalAccountService {

    void register(UserRegistrationDTO user) throws MultipleErrorResponse;

    void verified(String code, String mail) throws SingleErrorResponse;

    String login(UserLoginDTO user) throws SingleErrorResponse;

    UserDTO getMe() throws SingleErrorResponse;
}
