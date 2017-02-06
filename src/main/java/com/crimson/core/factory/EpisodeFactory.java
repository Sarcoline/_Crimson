package com.crimson.core.factory;

import com.crimson.core.model.Episode;


public class EpisodeFactory {

    public Episode getEpisode(String title) {


        Episode episode = null;

        switch (title.toLowerCase()) {
            case "episode 1": {
                episode = Episode.builder()
                        .title("Episode 1")
                        .build();
                break;
            }

        }
        return episode;
    }
}