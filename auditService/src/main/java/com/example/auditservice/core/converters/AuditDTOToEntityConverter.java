package com.example.auditservice.core.converters;

import com.example.auditservice.core.dtos.AuditDTO;
import com.example.auditservice.core.dtos.UserDTO;
import com.example.auditservice.dao.entities.AuditEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuditDTOToEntityConverter implements Converter<AuditDTO, AuditEntity> {
    @Override
    public AuditEntity convert(AuditDTO source) {
        AuditEntity entity = new AuditEntity();
        entity.setUuid(source.getUuid());
        entity.setDtCreate(source.getDtCreate());
        UserDTO user = source.getUser();
        entity.setUserUuid(user.getUuid());
        entity.setFio(user.getFio());
        entity.setMail(user.getMail());
        entity.setRole(user.getRole());
        entity.setText(source.getText());
        entity.setId(source.getId());
        entity.setType(source.getType());
        return entity;
    }
}
