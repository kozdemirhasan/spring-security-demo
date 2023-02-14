package de.kozdemir.springsecuritydemo;

import de.kozdemir.springsecuritydemo.model.User;
import de.kozdemir.springsecuritydemo.model.UserRole;
import de.kozdemir.springsecuritydemo.model.UserStatus;
import de.kozdemir.springsecuritydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringsecuritydemoApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Value("${db.admin.user.reset}")
    @Value("${db.admin.user.reset}")
    private boolean adminReset;

    public static void main(String[] args) {
        SpringApplication.run(SpringsecuritydemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        if (adminReset) {
            userRepository.deleteAll();
            User user = new User();
            user.setUsername("admin");
            user.setEmail("admin@shield.org");
            user.setPassword(passwordEncoder.encode("geheim123"));
            user.setRole(UserRole.ADMIN);
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);

            user = new User();
            user.setUsername("p.parker");
            user.setEmail("p.parker@shield.org");
            user.setPassword(passwordEncoder.encode("geheim123"));
            user.setRole(UserRole.USER);
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);

            user = new User();
            user.setUsername("b.banner");
            user.setEmail("b.banner@shield.org");
            user.setPassword(passwordEncoder.encode("geheim123"));
            user.setRole(UserRole.USER);
            user.setStatus(UserStatus.BLOCKED);
            userRepository.save(user);
        }
    }
}
