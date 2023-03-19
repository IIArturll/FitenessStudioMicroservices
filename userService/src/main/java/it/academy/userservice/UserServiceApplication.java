package it.academy.userservice;

import it.academy.userservice.core.properties.JwtProperty;
import it.academy.userservice.core.properties.KafkaProperty;
import it.academy.userservice.core.properties.MailProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({JwtProperty.class, MailProperty.class, KafkaProperty.class})
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
