package it.academy.userservice.services;

import it.academy.userservice.core.properties.MailProperty;
import it.academy.userservice.services.api.IMailService;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MailService implements IMailService {
    private final MailProperty mailProperty;

    public MailService(MailProperty mailProperty) {
        this.mailProperty = mailProperty;
    }

    @Override
    public void send(String mail) {
        String requestParams = "email=" + mail;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(requestParams, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(
                mailProperty.getSendUrl(),
                HttpMethod.POST,
                request,
                String.class
        );
        if (response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("failed to send code");
        }
    }

    @Override
    public boolean checkVerification(String mail, String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = mailProperty.getVerifyUrl() + "?email=" + mail + "&code=" + code;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        if (response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("verification failed");
        }
        return Boolean.TRUE.equals(response.getBody());
    }
}
