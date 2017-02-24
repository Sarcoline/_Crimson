package com.crimson.core.dao;

import com.crimson.core.model.Setting;
import com.crimson.core.model.User;

import java.util.List;

public interface SettingsDAO {

    void save(Setting setting);

    List<Setting> getAllSettings();

    Setting getSettingById(Long id);

    void delete(Setting setting);

    void update(Setting setting);

    void addUser2Setting(User user, Setting setting);

    void deleteUserFromSetting(User user, Setting setting);
}
