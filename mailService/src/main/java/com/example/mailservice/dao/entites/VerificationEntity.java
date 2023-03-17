package com.example.mailservice.dao.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "verification", schema = "fitness")
public class VerificationEntity {
    @Id
    private String email;
    private String code;

    public VerificationEntity() {
    }

    public VerificationEntity(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
