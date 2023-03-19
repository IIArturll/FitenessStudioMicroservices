package com.example.auditservice.dao.repositories;

import com.example.auditservice.dao.entities.AuditEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface IAuditRepository extends CrudRepository<AuditEntity, UUID>,
        PagingAndSortingRepository<AuditEntity,UUID> {
}
