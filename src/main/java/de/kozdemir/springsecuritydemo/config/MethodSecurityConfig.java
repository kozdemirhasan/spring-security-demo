package de.kozdemir.springsecuritydemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
//@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // Absicherung mit @Secured
@EnableMethodSecurity // Absicherung mit @PreAutorize
public class MethodSecurityConfig {


}
