package com.crimson.core.service;


import com.crimson.core.model.Setting;
import com.crimson.core.model.User;

import java.util.List;

public interface SettingsService {

    void saveSetting(Setting setting);

    List<Setting> getAllSettings();

    Setting getSettingById(Long id);

    void deleteSetting(Setting setting);

    void updateSetting(Setting setting);

    void addUser2Setting(User user, Setting setting);

    void deleteUserFromSetting(User user, Setting setting);
}
