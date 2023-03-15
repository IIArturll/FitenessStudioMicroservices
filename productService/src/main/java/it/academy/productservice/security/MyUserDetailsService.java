package it.academy.productservice.security;

import it.academy.productservice.core.properties.UserDetailsServiceProperty;
import it.academy.productservice.core.userdetails.MyUserDetails;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MyUserDetailsService implements UserDetailsService {

    private final UserDetailsServiceProperty serviceProperty;

    public MyUserDetailsService(UserDetailsServiceProperty serviceProperty) {
        this.serviceProperty = serviceProperty;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MyUserDetails> entity = new HttpEntity<>(headers);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<MyUserDetails> response = restTemplate.exchange(
                serviceProperty.getUrl() + username,
                HttpMethod.GET,
                entity,
                MyUserDetails.class
        );
        return response.getBody();
    }
}
