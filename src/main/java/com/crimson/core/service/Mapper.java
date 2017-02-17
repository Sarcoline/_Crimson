package com.crimson.core.service;

import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.ImageDTO;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {
        factory.classMap(User.class, UserDTO.class)
                .byDefault()
                .register();

        factory.classMap(TvShow.class, TvShowDTO.class)
                .byDefault()
                .register();
        factory.classMap(TvShow.class, ImageDTO.class)
                .byDefault()
                .register();
        factory.classMap(Episode.class, EpisodeDTO.class)
                .byDefault()
                .register();
    }
}