package com.crimson.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Meow on 25.03.2017.
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ResetPasswordMatchesValidator.class)
@Documented
public @interface ResetPasswordMatches {

    String message() default "Password mismatch";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
