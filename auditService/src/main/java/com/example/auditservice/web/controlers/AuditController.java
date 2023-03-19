package com.example.auditservice.web.controlers;

import com.example.auditservice.core.dtos.AuditDTO;
import com.example.auditservice.services.api.IAuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final IAuditService service;

    public AuditController(IAuditService service) {
        this.service = service;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AuditDTO> getByUUID(@PathVariable(value = "uuid") UUID uuid) {
        return ResponseEntity.status(200).body(service.get(uuid));
    }

    @GetMapping
    public ResponseEntity<Page<AuditDTO>> getPage(Pageable page) {
        return ResponseEntity.status(200).body(service.getPage(page));
    }
}
