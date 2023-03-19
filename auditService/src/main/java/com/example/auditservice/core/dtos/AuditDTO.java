package com.example.auditservice.core.dtos;


import com.example.auditservice.core.converters.InstantToLong;
import com.example.auditservice.core.converters.LongToInstant;
import com.example.auditservice.core.enums.EssenceType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;
import java.util.UUID;

public class AuditDTO {
    private UUID uuid;
    @JsonSerialize(converter = InstantToLong.class)
    @JsonDeserialize(converter = LongToInstant.class)
    private Instant dtCreate;
    private UserDTO user;
    private String text;
    private EssenceType type;
    private String id;

    public AuditDTO() {
    }

    public AuditDTO(UUID uuid, Instant dtCreate, UserDTO user, String text, EssenceType type, String id) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Instant getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Instant dtCreate) {
        this.dtCreate = dtCreate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EssenceType getType() {
        return type;
    }

    public void setType(EssenceType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
