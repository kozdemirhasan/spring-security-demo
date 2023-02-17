package de.kozdemir.springsecuritydemo.annotation;



import de.kozdemir.springsecuritydemo.validator.PasswordConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface Password {

    String message() default "..."; // TODO: Default-Message anpassen

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
