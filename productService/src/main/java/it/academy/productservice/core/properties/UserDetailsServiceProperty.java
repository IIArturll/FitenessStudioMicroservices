package it.academy.productservice.core.properties;

import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceProperty {
    private String url = "http://userService:8080/users/userDetails/mail/";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
