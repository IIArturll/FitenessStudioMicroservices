package it.academy.userservice.repositories.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "fitness")
public class UserEntity {
    @Id()
    @GeneratedValue
    @Column(name = "id")
    private UUID uuid;
    @Column(name = "dt_create")
    private Instant dtCreate;
    @Column(name = "dt_update")
    @Version
    private Instant dtUpdate;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",
            message = "illegal format of email,correct example: email@mail.ru , google@gmail.com")
    private String mail;
    @NotNull
    @NotBlank
    private String fio;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "role")
    private UserRoleEntity role;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "status")
    private UserStatusEntity status;
    @NotNull
    @NotBlank
    private String password;

    public UserEntity() {
    }

    public UserEntity(UUID uuid, Instant dtCreate, Instant dtUpdate, String mail,
                      String fio, UserRoleEntity role, UserStatusEntity status,
                      String password) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
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

    public Instant getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Instant dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public UserRoleEntity getRole() {
        return role;
    }

    public void setRole(UserRoleEntity role) {
        this.role = role;
    }

    public UserStatusEntity getStatus() {
        return status;
    }

    public void setStatus(UserStatusEntity status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
