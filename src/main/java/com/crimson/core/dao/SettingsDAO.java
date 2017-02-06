package com.crimson.core.dao;

import com.crimson.core.model.Setting;

import java.util.List;

public interface SettingsDAO {

    void saveSetting(Setting setting);

    List<Setting> getAllSettings();

    Setting getSettingById(Long id);

    void deleteSetting(Setting setting);

    void updateSetting(Setting setting);
}
