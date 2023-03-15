package it.academy.userservice.services;

import it.academy.userservice.core.exceptions.SingleErrorResponse;
import it.academy.userservice.core.user.dtos.UserDTO;
import it.academy.userservice.core.user.dtos.UserLoginDTO;
import it.academy.userservice.core.user.dtos.UserRegistrationDTO;
import it.academy.userservice.core.user.dtos.enums.UserStatus;
import it.academy.userservice.core.user.mappers.UserConverter;
import it.academy.userservice.repositories.api.IPersonalAccountRepository;
import it.academy.userservice.repositories.entity.UserEntity;
import it.academy.userservice.repositories.entity.UserStatusEntity;
import it.academy.userservice.security.JwtTokenUtil;
import it.academy.userservice.security.UserHolder;
import it.academy.userservice.services.api.IPersonalAccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PersonalAccountService implements IPersonalAccountService {

    private final IPersonalAccountRepository repository;
    private final UserConverter converter;
    private final PasswordEncoder encoder;
    private final UserHolder userHolder;
    private final JwtTokenUtil tokenUtil;

    public PersonalAccountService(IPersonalAccountRepository repository,
                                  UserConverter converter,
                                  PasswordEncoder encoder,
                                  UserHolder userHolder, JwtTokenUtil tokenUtil) {
        this.repository = repository;
        this.converter = converter;
        this.encoder = encoder;
        this.userHolder = userHolder;
        this.tokenUtil = tokenUtil;
    }

    @Override
    public void register(UserRegistrationDTO user) {
        UserEntity entity = converter.converToUserEntity(user);
        entity.setDtCreate(Instant.now());
        entity.setDtUpdate(Instant.now());
        repository.save(entity);
    }

    @Override
    public void verified(String code, String mail) throws SingleErrorResponse {
        UserEntity user = repository.findByMail(mail).orElseThrow(() ->
                new SingleErrorResponse("error", "user with this mail: " + mail
                        + " not found"));
        if (user.getUuid().toString().equals(code)) {
            user.setStatus(new UserStatusEntity(UserStatus.ACTIVATED));
            repository.save(user);
        } else {
            throw new SingleErrorResponse("error", "invalid verification code");
        }
    }

    @Override
    public String login(UserLoginDTO user) throws SingleErrorResponse {
        UserEntity entity = repository.findByMail(user.getMail()).orElseThrow(() ->
                new SingleErrorResponse("error", "user with email: " + user.getMail()
                        + " not found"));
        if (entity.getStatus().getStatus() == UserStatus.WAITING_ACTIVATION) {
            throw new SingleErrorResponse("error", "authorization is not available," +
                    " the account is not verified");
        }

        if (encoder.matches(user.getPassword(), entity.getPassword())) {
            return tokenUtil.generateAccessToken(user.getMail());
        } else {
            throw new SingleErrorResponse("error", "invalid password");
        }
    }

    @Override
    public UserDTO getMe() throws SingleErrorResponse {
        return converter.convertToUserDTO(
                repository.findByMail(
                                userHolder.getUser().getUsername())
                        .orElseThrow(() ->
                                new SingleErrorResponse("error",
                                        "user with this email not found")));
    }
}
