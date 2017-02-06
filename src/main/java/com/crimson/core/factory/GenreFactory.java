package com.crimson.core.factory;

import com.crimson.core.model.Genre;

public class GenreFactory {

    public Genre getGenre(String name) {
        Genre genre = null;

        switch (name.toLowerCase()) {
            case "drama": {
                genre = new Genre.Builder()
                        .name("Drama")
                        .build();
                break;
            }
        }
        return genre;
    }
}
