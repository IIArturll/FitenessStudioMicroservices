package it.academy.userservice.repositories.api;


import it.academy.userservice.repositories.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends PagingAndSortingRepository<UserEntity, UUID>,
        CrudRepository<UserEntity, UUID> {
    boolean existsByMail(String mail);
}
