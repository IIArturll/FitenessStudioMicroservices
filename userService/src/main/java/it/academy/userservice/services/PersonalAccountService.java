package it.academy.userservice.services;

import it.academy.userservice.core.exceptions.SingleErrorResponse;
import it.academy.userservice.core.properties.MailProperty;
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
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.UUID;

@Service
public class PersonalAccountService implements IPersonalAccountService {

    private final IPersonalAccountRepository repository;
    private final UserConverter converter;
    private final PasswordEncoder encoder;
    private final UserHolder userHolder;
    private final JwtTokenUtil tokenUtil;
    private final MailProperty mailProperty;

    public PersonalAccountService(IPersonalAccountRepository repository,
                                  UserConverter converter,
                                  PasswordEncoder encoder,
                                  UserHolder userHolder,
                                  JwtTokenUtil tokenUtil,
                                  MailProperty mailProperty) {
        this.repository = repository;
        this.converter = converter;
        this.encoder = encoder;
        this.userHolder = userHolder;
        this.tokenUtil = tokenUtil;
        this.mailProperty = mailProperty;
    }

    @Override
    public void register(UserRegistrationDTO user) throws SingleErrorResponse {
        if (repository.findByMail(user.getMail()).isPresent()) {
            throw new SingleErrorResponse("error", "user with this mail already exist");
        }
        UserEntity entity = converter.converToUserEntity(user);
        entity.setUuid(UUID.randomUUID());
        entity.setDtCreate(Instant.now());
        entity.setDtUpdate(Instant.now());
        repository.save(entity);
        sendMail(user.getMail());
    }

    @Override
    public void verified(String code, String mail) throws SingleErrorResponse {
        UserEntity user = repository.findByMail(mail).orElseThrow(() ->
                new SingleErrorResponse("error", "user with this mail: " + mail
                        + " not found"));

        if (checkVerification(mail, code)) {
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

    private void sendMail(String mail) {
        String requestParams = "email=" + mail;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(requestParams, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(
                mailProperty.getSendUrl(),
                HttpMethod.POST,
                request,
                String.class
        );
        if(response.getStatusCode().is5xxServerError()){
            throw new RuntimeException("failed to send code");
        }
    }

    private boolean checkVerification(String mail, String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = mailProperty.getVerifyUrl() + "?email=" + mail + "&code=" + code;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        if (response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("verification failed");
        }
        return Boolean.TRUE.equals(response.getBody());
    }
}
