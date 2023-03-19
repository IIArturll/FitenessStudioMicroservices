package com.example.auditservice.core.converters;

import com.example.auditservice.core.dtos.AuditDTO;
import com.example.auditservice.core.dtos.UserDTO;
import com.example.auditservice.dao.entities.AuditEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuditEntityTODTOConverter implements Converter<AuditEntity, AuditDTO> {
    @Override
    public AuditDTO convert(AuditEntity source) {
        AuditDTO auditDTO = new AuditDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(source.getUserUuid());
        userDTO.setFio(source.getFio());
        userDTO.setMail(source.getMail());
        userDTO.setRole(source.getRole());

        auditDTO.setUuid(source.getUuid());
        auditDTO.setDtCreate(source.getDtCreate());
        auditDTO.setUser(userDTO);
        auditDTO.setText(source.getText());
        auditDTO.setType(source.getType());
        auditDTO.setId(source.getId());
        return auditDTO;
    }
}
