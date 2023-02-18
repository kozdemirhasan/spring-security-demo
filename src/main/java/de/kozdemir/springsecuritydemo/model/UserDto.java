package de.kozdemir.springsecuritydemo.model;

import de.kozdemir.springsecuritydemo.annotation.Password;
import de.kozdemir.springsecuritydemo.annotation.PasswordMatch;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {

    @NotEmpty
    private String username;

    @Email(regexp = ".*")
    @NotEmpty
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,25}$", message = "Passwort ist ungültig.")
    private String password;

//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,25}$", message = "Passwort ist ungültig.")
    @Password // spezial Annotation
    private String passwordConfirmation;


    public User convert(PasswordEncoder passwordEncoder) {

        User user = new User();
        user.setUsername(username.toLowerCase());
        user.setEmail(email.toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.CREATED);
        return user;
    }

    //getter- setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
