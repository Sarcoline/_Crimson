package com.crimson.core.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * Created by Meow on 14.04.2017.
 */
public @Data class SettingDTO {
    private Long id;

    private Boolean sendEpisodeList;

    @Range(max = 99, message = "{invalid.numberOfEpisodesOnUserPage}")
    private int numberOfEpisodesOnUserPage;


    @Range(max = 30, message = "{invalid.daysOfUpcomingEpisodes}")
    private int daysOfUpcomingEpisodes;
}
