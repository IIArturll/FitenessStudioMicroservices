package it.academy.userservice.services;

import it.academy.userservice.audit.annotations.Audit;
import it.academy.userservice.core.exceptions.SingleErrorResponse;
import it.academy.userservice.core.user.dtos.MyUserDetails;
import it.academy.userservice.core.user.dtos.UserCreateDTO;
import it.academy.userservice.core.user.dtos.UserDTO;
import it.academy.userservice.core.user.mappers.UserConverter;
import it.academy.userservice.repositories.api.IUserRepository;
import it.academy.userservice.repositories.entity.UserEntity;
import it.academy.userservice.repositories.entity.UserRoleEntity;
import it.academy.userservice.repositories.entity.UserStatusEntity;
import it.academy.userservice.security.MyUserDetailsService;
import it.academy.userservice.services.api.IMailService;
import it.academy.userservice.services.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final IUserRepository repository;
    private final UserConverter converter;
    private final MyUserDetailsService userDetailsService;
    private final IMailService mailService;

    public UserService(IUserRepository repository,
                       UserConverter converter,
                       MyUserDetailsService userDetailsService,
                       IMailService mailService) {
        this.repository = repository;
        this.converter = converter;
        this.userDetailsService = userDetailsService;
        this.mailService = mailService;
    }

    @Override
    @Audit(message = "create")
    public UUID create(UserCreateDTO user) {
        if (repository.existsByMail(user.getMail())) {
            throw new SingleErrorResponse("error", "user with this email already exist");
        }
        UserEntity entity = converter.convertToUserEntity(user);
        UUID uuid = UUID.randomUUID();
        entity.setUuid(uuid);
        entity.setDtCreate(Instant.now());
        entity.setDtUpdate(Instant.now());
        repository.save(entity);
        //CompletableFuture.runAsync(() -> mailService.send(entity.getMail()));
        return uuid;
    }

    @Override
    public UserDTO get(UUID uuid) {
        UserEntity user = repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error", "invalid user uuid"));
        return converter.convertToUserDTO(user);
    }

    @Override
    @Audit(message = "update")
    public UUID update(UUID uuid, Instant dtUpdate, UserCreateDTO createDTO) {
        UserEntity user = repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error", "invalid user uuid"));
        if (dtUpdate.toEpochMilli() != user.getDtUpdate().toEpochMilli()) {
            throw new SingleErrorResponse("error", "user has already been updated");
        }
        user.setMail(createDTO.getMail());
        user.setFio(createDTO.getFio());
        user.setRole(new UserRoleEntity(createDTO.getRole()));
        user.setStatus(new UserStatusEntity(createDTO.getStatus()));
        user.setPassword(createDTO.getPassword());
        repository.save(user);
        return uuid;
    }

    @Override
    public Page<UserDTO> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(converter::convertToUserDTO);
    }

    @Override
    public MyUserDetails getUserDetails(String mail) {
        return userDetailsService.loadUserByUsername(mail);
    }
}
