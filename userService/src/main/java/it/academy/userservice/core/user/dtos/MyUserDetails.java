package it.academy.userservice.core.user.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.academy.userservice.core.user.dtos.enums.UserRole;
import it.academy.userservice.core.user.dtos.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@JsonIgnoreProperties({"enabled","username","authorities","accountNonExpired",
        "credentialsNonExpired","accountNonLocked"})
public class MyUserDetails implements UserDetails {
    private UUID uuid;
    private Instant dtCreate;
    private Instant dtUpdate;
    private String mail;
    private String fio;
    private UserRole role;
    private UserStatus status;
    private String password;

    public MyUserDetails(UUID uuid, Instant dtCreate, Instant dtUpdate, String mail,
                         String fio, UserRole role, UserStatus status, String password) {
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == UserStatus.DEACTIVATED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == UserStatus.DEACTIVATED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVATED;
    }
}
