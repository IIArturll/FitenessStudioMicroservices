package com.example.mailservice.services;

import com.example.mailservice.core.exceptions.SingleErrorResponse;
import com.example.mailservice.core.properties.MailProperty;
import com.example.mailservice.dao.entites.VerificationEntity;
import com.example.mailservice.dao.repositories.IVerificationRepository;
import com.example.mailservice.services.api.ISenderService;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class EmailService implements ISenderService {

    private final JavaMailSender emailSender;
    private final IVerificationRepository repository;
    private final MailProperty mailProperty;

    public EmailService(JavaMailSender emailSender,
                        IVerificationRepository repository,
                        MailProperty mailProperty) {
        this.emailSender = emailSender;
        this.repository = repository;
        this.mailProperty = mailProperty;
    }

    @Job(name = "sendVerificationCode", retries = 5)
    public void sendVerificationCode(String mail, String code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailProperty.getFromAddress());
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject(mailProperty.getSubject());
        simpleMailMessage.setText(String.format("Your verification code is : %s  \n %s?mail=%s&code=%s",
                code, mailProperty.getUrl(), mail, code));
        emailSender.send(simpleMailMessage);
    }


    @Override
    public void send(String recipient) {
        String code = UUID.randomUUID().toString();
        repository.save(new VerificationEntity(recipient, code));
        BackgroundJob.enqueue(() -> sendVerificationCode(recipient, code));
    }

    @Override
    public boolean verify(String recipient, String code) {
        VerificationEntity entity = repository.findById(recipient).orElseThrow(() -> new SingleErrorResponse("error",
                "user with mail: " + recipient + " not found"));
        return Objects.equals(entity.getCode(), code);
    }


}