package de.kozdemir.springsecuritydemo.config;


import de.kozdemir.springsecuritydemo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails admin = User.withUsername("admin")
                                .password(passwordEncoder().encode("geheim"))
                                .roles("ADMIN")
                                .build();

        UserDetails user = User.withUsername("p.parker")
                .password(passwordEncoder().encode("geheim"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
    */


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/webjars/**", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                //.httpBasic()
                .formLogin()
                    .loginPage("/login").failureUrl("/login/error")
                .and()
                    .authorizeRequests()
                        .antMatchers("/", "/login/**", "/register/**", "/h2-console/**").permitAll() //Frei zugänglich
                        //.antMatchers("/admin/**").hasRole("ADMIN") //Freigabe nu mit bestimmten Rolle
                        .anyRequest().authenticated() // Alle anderen erfordern anmeldung
                .and()
                    .logout().logoutUrl("/logout")
                        .invalidateHttpSession(true); //Session wird ungültig
//                        .deleteCookies("JSESSIONID"); //Session- cookie wird im Bowser gelöst

        return http.build();
    }


}
