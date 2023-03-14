package it.academy.userservice.security;

import it.academy.userservice.core.user.mappers.UserConverter;
import it.academy.userservice.repositories.api.IPersonalAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    private final IPersonalAccountRepository repository;
    private final UserConverter converter;

    public MyUserDetailsService(IPersonalAccountRepository repository,
                                UserConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return converter.convertToUserDetails(repository.findByMail(username).orElseThrow(() ->
                new UsernameNotFoundException("user with this mail not found")));
    }
}
