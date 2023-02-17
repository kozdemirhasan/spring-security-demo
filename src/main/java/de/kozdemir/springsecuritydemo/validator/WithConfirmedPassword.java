package de.kozdemir.springsecuritydemo.validator;


import de.kozdemir.springsecuritydemo.annotation.PasswordMatch;

@PasswordMatch
public interface WithConfirmedPassword {

    String getPassword();

    String getPasswordConfirmation();
}
