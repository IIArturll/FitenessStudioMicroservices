package it.academy.productservice.security;

import it.academy.productservice.core.userdetails.MyUserDetails;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;


public class MyUserDetailsService implements UserDetailsService {

    private final String url = "http://userService:8080/users/userDetails/mail/";

    public MyUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MyUserDetails> entity = new HttpEntity<>(headers);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<MyUserDetails> response = restTemplate.exchange(
                url + username,
                HttpMethod.GET,
                entity,
                MyUserDetails.class
        );
        return response.getBody();
    }
}
