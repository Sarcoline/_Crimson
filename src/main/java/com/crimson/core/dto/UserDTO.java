package com.crimson.core.dto;

import com.crimson.core.model.Rating;
import com.crimson.core.model.Role;
import com.crimson.core.validator.PasswordMatches;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@PasswordMatches
public @Data class UserDTO {

    private Long id;

    @Size(min = 3, max = 30)
    @Pattern(regexp = "[A-Za-z0-9]*")
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String password;

    private String matchingPassword;

    private byte[] profilePic;

    private MultipartFile uploadedPic;

    private List<Role> roles = new ArrayList<>();

    private List<Rating> userRatings;

    private List<TvShowDTO> userTvShowList;

    private List<EpisodeDTO> userEpisodeList;

}
