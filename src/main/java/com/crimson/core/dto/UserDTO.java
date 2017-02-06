package com.crimson.core.dto;

import com.crimson.core.model.Episode;
import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserDTO {

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

    private byte[] profilePic;
    private String role = "ROLE_USER";

    private List<Rating> userRatings;

    private List<TvShow> userTvShowList;

    private List<Episode> userEpisodeList;

}
