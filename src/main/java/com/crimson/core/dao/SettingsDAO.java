package com.crimson.core.dao;

import com.crimson.core.model.Setting;
import com.crimson.core.model.User;

public interface SettingsDAO extends BaseDAO<Setting, Long> {

    void addUser2Setting(User user, Setting setting);

    void deleteUserFromSetting(Setting setting);
}
