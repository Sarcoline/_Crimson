package com.crimson.core.service;

import com.crimson.core.dto.*;
import com.crimson.core.model.*;
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
        factory.classMap(TvShow.class, TvShowSearchDTO.class)
                .byDefault()
                .register();
        factory.classMap(Episode.class, EpisodeFormDTO.class)
                .byDefault()
                .register();
        factory.classMap(Comment.class, CommentDTO.class)
                .byDefault()
                .register();
        factory.classMap(FilterResponse.class, FilterResponseDTO.class)
                .byDefault()
                .register();
        factory.classMap(ReviewDTO.class, Review.class)
                .byDefault()
                .register();
        factory.classMap(TvShowAddDTO.class, TvShow.class)
                .byDefault()
                .register();
        factory.getConverterFactory().registerConverter(new LocalDateConverter());
    }
}