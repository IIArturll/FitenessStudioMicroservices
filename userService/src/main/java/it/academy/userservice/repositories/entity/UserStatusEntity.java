package it.academy.userservice.repositories.entity;


import it.academy.userservice.core.user.dtos.enums.UserStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "user_status", schema = "fitness")
public class UserStatusEntity {
    @Id
    @GeneratedValue(generator = "status_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "status_seq", sequenceName = "user_status_id_seq",
            schema = "fitness", allocationSize = 1)
    private Short id;
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public UserStatusEntity() {
    }

    public UserStatusEntity(UserStatus status) {
        this.status = status;
        this.id = (short) (status.ordinal() + 1);
    }

    public Short getId() {
        return id;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
