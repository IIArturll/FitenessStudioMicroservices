package it.academy.userservice.services;


import it.academy.userservice.audit.annotations.Audit;
import it.academy.userservice.core.exceptions.SingleErrorResponse;
import it.academy.userservice.core.user.dtos.MyUserDetails;
import it.academy.userservice.core.user.dtos.UserDTO;
import it.academy.userservice.core.user.dtos.UserLoginDTO;
import it.academy.userservice.core.user.dtos.UserRegistrationDTO;
import it.academy.userservice.core.user.dtos.enums.UserStatus;
import it.academy.userservice.core.user.mappers.UserConverter;
import it.academy.userservice.repositories.api.IPersonalAccountRepository;
import it.academy.userservice.repositories.entity.UserEntity;
import it.academy.userservice.repositories.entity.UserStatusEntity;
import it.academy.userservice.security.JwtTokenUtil;
import it.academy.userservice.security.MyUserDetailsService;
import it.academy.userservice.security.UserHolder;
import it.academy.userservice.services.api.IMailService;
import it.academy.userservice.services.api.IPersonalAccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class PersonalAccountService implements IPersonalAccountService {

    private final IPersonalAccountRepository repository;
    private final UserConverter converter;
    private final PasswordEncoder encoder;
    private final UserHolder userHolder;
    private final JwtTokenUtil tokenUtil;
    private final MyUserDetailsService userDetailsService;
    private final IMailService mailService;

    public PersonalAccountService(IPersonalAccountRepository repository,
                                  UserConverter converter,
                                  PasswordEncoder encoder,
                                  UserHolder userHolder,
                                  JwtTokenUtil tokenUtil,
                                  MyUserDetailsService userDetailsService,
                                  IMailService mailService) {
        this.repository = repository;
        this.converter = converter;
        this.encoder = encoder;
        this.userHolder = userHolder;
        this.tokenUtil = tokenUtil;
        this.userDetailsService = userDetailsService;
        this.mailService = mailService;
    }

    @Override
    @Audit(message = "registration")
    public UUID register(UserRegistrationDTO user) {
        if (repository.existsByMail(user.getMail())) {
            throw new SingleErrorResponse("error", "user with this mail already exist");
        }
        UserEntity entity = converter.converToUserEntity(user);
        entity.setUuid(UUID.randomUUID());
        entity.setDtCreate(Instant.now());
        entity.setDtUpdate(Instant.now());
        repository.save(entity);
        CompletableFuture.runAsync(() -> mailService.send(user.getMail()));
        return entity.getUuid();
    }

    @Override
    @Audit(message = "verification")
    public UUID verified(String code, String mail) {
        UserEntity user = repository.findByMail(mail).orElseThrow(() ->
                new SingleErrorResponse("error", "user with this mail: " + mail
                        + " not found"));

        if (mailService.checkVerification(mail, code)) {
            user.setStatus(new UserStatusEntity(UserStatus.ACTIVATED));
            repository.save(user);
        } else {
            throw new SingleErrorResponse("error", "invalid verification code");
        }
        return user.getUuid();
    }

    @Override
    public String login(UserLoginDTO user) {
        UserEntity entity = repository.findByMail(user.getMail()).orElseThrow(() ->
                new SingleErrorResponse("error", "user with email: " + user.getMail()
                        + " not found"));
        if (entity.getStatus().getStatus() == UserStatus.WAITING_ACTIVATION) {
            throw new SingleErrorResponse("error", "authorization is not available," +
                    " the account is not verified");
        }
        if (encoder.matches(user.getPassword(), entity.getPassword())) {
            MyUserDetails userDetails = userDetailsService.loadUserByUsername(user.getMail());
            return tokenUtil.generateAccessToken(userDetails);
        } else {
            throw new SingleErrorResponse("error", "invalid password");
        }
    }

    @Override
    public UserDTO getMe() {
        return converter.convertToUserDTO(
                repository.findByMail(
                                userHolder.getUser().getUsername())
                        .orElseThrow(() ->
                                new SingleErrorResponse("error",
                                        "user with this email not found")));
    }

}
