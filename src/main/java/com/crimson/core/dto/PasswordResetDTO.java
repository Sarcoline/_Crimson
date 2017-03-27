package com.crimson.core.dto;

import com.crimson.core.validator.ResetPasswordMatches;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@ResetPasswordMatches
public @Data class PasswordResetDTO {

    @NotEmpty
    @Size(min = 3, max = 100)
    private String password;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String matchingPassword;

    private String token;
}
