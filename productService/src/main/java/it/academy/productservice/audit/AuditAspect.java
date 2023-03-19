package it.academy.productservice.audit;

import it.academy.productservice.audit.annotations.Audit;
import it.academy.productservice.audit.dtos.AuditDTO;
import it.academy.productservice.audit.dtos.AuditUserDTO;
import it.academy.productservice.core.userdetails.MyUserDetails;
import it.academy.productservice.security.UserHolder;
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
        MyUserDetails user = (MyUserDetails) userHolder.getUser();
        AuditUserDTO userDTO = new AuditUserDTO();
        userDTO.setUuid(user.getUuid());
        userDTO.setMail(user.getMail());
        userDTO.setFio(user.getFio());
        userDTO.setRole(user.getRole());

        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setUuid(UUID.randomUUID());
        auditDTO.setDtCreate(Instant.now());
        auditDTO.setUser(userDTO);
        auditDTO.setText(audit.message());
        auditDTO.setType(audit.essence());
        auditDTO.setId(uuid.toString());
        kafkaTemplate.send("audit", auditDTO);
    }
}
