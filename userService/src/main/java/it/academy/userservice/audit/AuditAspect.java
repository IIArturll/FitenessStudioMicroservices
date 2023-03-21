package it.academy.userservice.audit;

import it.academy.userservice.audit.annotations.Audit;
import it.academy.userservice.audit.dtos.AuditDTO;
import it.academy.userservice.audit.dtos.AuditUserDTO;
import it.academy.userservice.audit.enums.EssenceType;
import it.academy.userservice.core.user.dtos.MyUserDetails;
import it.academy.userservice.core.user.dtos.enums.UserRole;
import it.academy.userservice.security.UserHolder;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Aspect
@Component
public class AuditAspect {

    private final UserHolder userHolder;
    private final UUID applicationUUID = UUID.fromString("9ef2810c-0f1e-49cf-b6fb-09a4a9c8c6df");
    KafkaTemplate<String, AuditDTO> kafkaTemplate;

    public AuditAspect(UserHolder userHolder, KafkaTemplate<String, AuditDTO> kafkaTemplate) {
        this.userHolder = userHolder;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Pointcut("@annotation(audit)")
    public void isAudit(Audit audit) {
    }


    @AfterReturning(value = "isAudit(audit)", argNames = "audit,uuid", returning = "uuid")
    public void send(Audit audit, UUID uuid) {
        AuditUserDTO auditUserDTO = new AuditUserDTO();
        if (audit.message().contains("registration") || audit.message().contains("verification")) {
            auditUserDTO.setUuid(applicationUUID);
            auditUserDTO.setFio("system");
            auditUserDTO.setMail("system");
            auditUserDTO.setRole(UserRole.USER);
        } else {
            MyUserDetails user = (MyUserDetails) userHolder.getUser();
            auditUserDTO.setUuid(user.getUuid());
            auditUserDTO.setMail(user.getMail());
            auditUserDTO.setFio(user.getFio());
            auditUserDTO.setRole(user.getRole());
        }
        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setUuid(UUID.randomUUID());
        auditDTO.setDtCreate(Instant.now());
        auditDTO.setUser(auditUserDTO);
        auditDTO.setText(audit.message());
        auditDTO.setType(EssenceType.USER);
        auditDTO.setId(uuid.toString());
        kafkaTemplate.send("audit", auditDTO);
    }
}
