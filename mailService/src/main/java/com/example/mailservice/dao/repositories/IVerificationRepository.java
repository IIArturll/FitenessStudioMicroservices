package com.example.mailservice.dao.repositories;

import com.example.mailservice.dao.entites.VerificationEntity;
import org.springframework.data.repository.CrudRepository;

public interface IVerificationRepository extends CrudRepository<VerificationEntity,String> {
}
