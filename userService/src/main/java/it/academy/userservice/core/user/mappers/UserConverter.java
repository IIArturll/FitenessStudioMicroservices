package it.academy.userservice.core.user.mappers;

import it.academy.userservice.core.user.dtos.MyUserDetails;
import it.academy.userservice.core.user.dtos.UserCreateDTO;
import it.academy.userservice.core.user.dtos.UserDTO;
import it.academy.userservice.core.user.dtos.UserRegistrationDTO;
import it.academy.userservice.core.user.dtos.enums.UserRole;
import it.academy.userservice.core.user.dtos.enums.UserStatus;
import it.academy.userservice.repositories.entity.UserEntity;
import it.academy.userservice.repositories.entity.UserRoleEntity;
import it.academy.userservice.repositories.entity.UserStatusEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

    private final PasswordEncoder encoder;

    public UserConverter(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public UserDTO convertToUserDTO(UserEntity entity) {
        return new UserDTO(entity.getUuid(), entity.getDtCreate(),
                entity.getDtUpdate(), entity.getMail(),
                entity.getFio(), entity.getRole().getRole(), entity.getStatus().getStatus());
    }

    public UserEntity convertToUserEntity(UserCreateDTO user) {
        UserEntity entity = new UserEntity();
        entity.setMail(user.getMail());
        entity.setFio(user.getFio());
        entity.setRole(new UserRoleEntity(user.getRole()));
        entity.setStatus(new UserStatusEntity(user.getStatus()));
        entity.setPassword(encoder.encode(user.getPassword()));
        return entity;
    }

    public UserEntity converToUserEntity(UserRegistrationDTO userDTO) {
        UserEntity entity = new UserEntity();
        entity.setMail(userDTO.getMail());
        entity.setFio(userDTO.getFio());
        entity.setPassword(encoder.encode(userDTO.getPassword()));
        entity.setRole(new UserRoleEntity(UserRole.USER));
        entity.setStatus(new UserStatusEntity(UserStatus.WAITING_ACTIVATION));
        return entity;
    }

    public MyUserDetails convertToUserDetails(UserEntity entity){
        return new MyUserDetails(entity.getUuid(),entity.getDtCreate(),entity.getDtUpdate(),
                entity.getMail(),entity.getFio(),entity.getRole().getRole(),
                entity.getStatus().getStatus(), entity.getPassword());
    }
}
