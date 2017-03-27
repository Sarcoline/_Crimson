package com.crimson.core.validator;

import com.crimson.core.dto.PasswordResetDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResetPasswordMatchesValidator implements ConstraintValidator<ResetPasswordMatches, Object> {

    @Override
    public void initialize(final ResetPasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final PasswordResetDTO password = (PasswordResetDTO) obj;
        return password.getPassword().equals(password.getMatchingPassword());
    }
}
