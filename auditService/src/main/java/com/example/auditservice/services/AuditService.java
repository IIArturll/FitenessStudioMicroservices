package com.example.auditservice.services;

import com.example.auditservice.core.converters.AuditDTOToEntityConverter;
import com.example.auditservice.core.converters.AuditEntityTODTOConverter;
import com.example.auditservice.core.dtos.AuditDTO;
import com.example.auditservice.core.exceptions.SingleErrorResponse;
import com.example.auditservice.dao.repositories.IAuditRepository;
import com.example.auditservice.services.api.IAuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuditService implements IAuditService {

    private final IAuditRepository repository;
    private final AuditEntityTODTOConverter converterToDTO;
    private final AuditDTOToEntityConverter converterToEntity;
    private final ObjectMapper objectMapper;

    public AuditService(IAuditRepository repository,
                        AuditEntityTODTOConverter converterToDTO,
                        AuditDTOToEntityConverter converterToEntity) {
        this.repository = repository;
        this.converterToDTO = converterToDTO;
        this.converterToEntity = converterToEntity;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Page<AuditDTO> getPage(Pageable page) {
        return repository.findAll(page).map(converterToDTO::convert);
    }

    @Override
    public AuditDTO get(UUID uuid) {
        return converterToDTO.convert(repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("not found")));
    }

    @KafkaListener(topics = "audit", groupId = "audit", autoStartup = "true")
    private void listen(String audit) {
        try {
            AuditDTO auditDTO = objectMapper.readValue(audit, AuditDTO.class);
            repository.save(converterToEntity.convert(auditDTO));
        } catch (JsonProcessingException e) {
            throw new SingleErrorResponse("server error", e.getLocalizedMessage());
        }
    }
}
