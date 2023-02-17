package de.kozdemir.springsecuritydemo.annotation;



import de.kozdemir.springsecuritydemo.validator.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {
    String message() default "{validation.password.match}"; // TODO: Default-Message anpassen

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
