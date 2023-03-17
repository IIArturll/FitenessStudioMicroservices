package it.academy.userservice.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtFilter jwtFilter) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.setStatus(
                                    HttpServletResponse.SC_UNAUTHORIZED
                            );
                        }
                ).accessDeniedHandler(((request, response, ex) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    })
                ).and();

        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST,"/users/registration").permitAll()
                .requestMatchers(HttpMethod.GET,"/users/verification").permitAll()
                .requestMatchers(HttpMethod.POST,"/users/login").permitAll()
                .requestMatchers(HttpMethod.GET,"/users/me").authenticated()
                .requestMatchers(HttpMethod.GET,"/users/userDetails/mail/{mail}").access(
                        new WebExpressionAuthorizationManager(
                                "hasIpAddress('productService')"
                        ))
                .requestMatchers("/users/**").hasRole("ADMIN")
                .anyRequest().denyAll()
        );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
