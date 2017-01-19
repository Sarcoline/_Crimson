package com.crimson.service;

import com.crimson.dto.TvShowDTO;
import com.crimson.dto.UserDTO;
import com.crimson.model.TvShow;
import com.crimson.model.User;
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
    }
}