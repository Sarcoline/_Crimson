package com.crimson.core.factory;

import com.crimson.core.model.Setting;

public class SettingFactory {

    public Setting getSetting(String name) {

        Setting setting = null;

        switch (name.toLowerCase()) {
            case "setting_1": {
                setting = Setting.builder()
                        .daysOfUpcomingEpisodes(10)
                        .sendEpisodeList(true)
                        .build();
                break;
            }

            case "setting_2": {
                setting = Setting.builder()
                        .daysOfUpcomingEpisodes(10)
                        .sendEpisodeList(false)
                        .build();
                break;
            }

            case "setting_3": {
                setting = Setting.builder()
                        .daysOfUpcomingEpisodes(7)
                        .sendEpisodeList(false)
                        .build();
                break;
            }
        }

        return setting;
    }
}
