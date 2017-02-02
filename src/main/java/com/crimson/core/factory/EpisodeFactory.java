package com.crimson.core.factory;

import com.crimson.core.model.Episode;

/**
 * Created by Meow on 02.02.2017.
 */
public class EpisodeFactory {

    public Episode getEpisode(String title) {


        Episode episode = null;

        switch (title.toLowerCase()) {
            case "episode 1": {
                episode = new Episode.Builder()
                        .title("Episode 1")
                        .build();
                break;
            }

        }
        return episode;
    }
}