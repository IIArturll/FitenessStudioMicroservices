package it.academy.userservice.repositories.entity;


import it.academy.userservice.core.user.dtos.enums.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "user_role", schema = "fitness")
public class UserRoleEntity {
    @Id
    @GeneratedValue(generator = "role_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_seq", sequenceName = "user_role_id_seq",
            schema = "fitness", allocationSize = 1)
    private Short id;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserRoleEntity() {
    }

    public UserRoleEntity(UserRole role) {
        this.role = role;
        this.id = (short) (role.ordinal() + 1);
    }

    public Short getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
