package com.crimson.core.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public @Data class PasswordDTO {


    @NotEmpty
    @Size(min = 3, max = 100)
    private String password;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String matchingPassword;

    private String oldPassword;
}
