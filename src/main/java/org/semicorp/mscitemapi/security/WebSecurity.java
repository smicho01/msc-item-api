package org.semicorp.mscitemapi.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebSecurity.class);

    @Override
    public void configure(HttpSecurity http) throws Exception {
        LOGGER.debug("Using Web Security");

        // Convert JWT authorities
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        http    .antMatcher("/**").csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/service/status")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/healthcheck").permitAll() // allow url without JWT token
                .antMatchers(HttpMethod.GET,"/api/v1/question/{questionId}").permitAll() // allow url without JWT token
                .antMatchers(HttpMethod.GET,"/api/v1/answer").permitAll() // allow url without JWT token
                .antMatchers(HttpMethod.GET,"/api/v1/answer/{questionId}").permitAll() // allow url without JWT token
                .antMatchers(HttpMethod.GET,"/api/v1/answer/{questionId}/status/{status}").permitAll() // allow url without JWT token
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);


    }
}
