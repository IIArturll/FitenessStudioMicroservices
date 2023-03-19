package it.academy.productservice;

import it.academy.productservice.core.properties.JwtProperty;
import it.academy.productservice.core.properties.KafkaProperty;
import it.academy.productservice.core.properties.UserDetailsServiceProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({UserDetailsServiceProperty.class, JwtProperty.class, KafkaProperty.class})
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
