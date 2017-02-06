package com.crimson.core.service;


import com.crimson.core.model.Setting;

import java.util.List;

public interface SettingsService {

    void saveSetting(Setting setting);

    List<Setting> getAllSettings();

    Setting getSettingById(Long id);

    void deleteSetting(Setting setting);

    void updateSetting(Setting setting);
}
