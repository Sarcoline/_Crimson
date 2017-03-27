package com.crimson.core.validator;

import com.crimson.core.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UserPasswordMatchesValidator implements ConstraintValidator<UserPasswordMatches, Object> {

    @Override
    public void initialize(final UserPasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDTO user = (UserDTO) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}

