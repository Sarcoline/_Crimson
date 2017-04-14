package com.crimson.core.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meow on 14.04.2017.
 */
public @Data class UserDisplayDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 30, message = "{invalid.size.name}")
    @Pattern(regexp = "[\\S]+", message = "{invalid.pattern.name}")
    private String name;

    @NotNull
    @Email(message = "{invalid.email}")
    private String email;

    private boolean active;

    private boolean adult;

    private SettingDTO setting;

    private String password;

    private String matchingPassword;

    private byte[] profilePic;

    private MultipartFile uploadedPic;

    private List<TvShowSearchDTO> tvShows = new ArrayList<>();

    private List<EpisodeFromJson> episodes = new ArrayList<>();
}
