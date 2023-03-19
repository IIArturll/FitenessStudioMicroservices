package com.example.auditservice.services.api;

import com.example.auditservice.core.dtos.AuditDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAuditService {
    Page<AuditDTO> getPage(Pageable page);
    AuditDTO get(UUID uuid);
}
