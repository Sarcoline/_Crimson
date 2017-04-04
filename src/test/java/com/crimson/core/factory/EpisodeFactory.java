package com.crimson.core.factory;

import com.crimson.core.model.Episode;


public class EpisodeFactory {

    public Episode getEpisode(String title) {


        Episode episode = null;

        switch (title.toLowerCase()) {
            case "episode_1": {
                episode = Episode.builder()
                        .title("Episode 1")
                        .build();
                break;
            }
            case "episode_2": {
                episode = Episode.builder()
                        .title("Episode 2")
                        .build();
                break;
            }
            case "episode_3": {
                episode = Episode.builder()
                        .title("Episode 3")
                        .build();
                break;
            }

        }
        return episode;
    }
}