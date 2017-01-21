package com.crimson.core.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@Data
public class UserDTO {

    private Long id;

    @Size(min = 3, max = 30)
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String password;

    private byte[] profilePic;

}
