package com.crimson.core.service;

import com.crimson.core.dao.SettingsDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.model.Setting;
import com.crimson.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsDAO settingsDAO;
    @Autowired
    private UserDAO userDAO;

    @Override
    public void saveSetting(Setting setting) {
        settingsDAO.save(setting);
    }

    @Override
    public List<Setting> getAllSettings() {
        return settingsDAO.getAll();
    }

    @Override
    public Setting getSettingById(Long id) {
        return settingsDAO.getById(id);
    }

    @Override
    public void deleteSetting(Setting setting) {
        settingsDAO.delete(setting);
    }

    @Override
    public void updateSetting(Setting setting) {
        settingsDAO.update(setting);
    }

    //RELATIONSHIPS

    //User2Setting

    @Override
    public void addUser2Setting(User user, Setting setting) {
        if (setting.getUser() != user) {
            settingsDAO.addUser2Setting(user, setting);
        }
        if (user.getSetting() != setting) {
            userDAO.addSetting2User(user, setting);
        }
    }

    @Override
    public void deleteUserFromSetting(User user, Setting setting) {
        if (setting.getUser() == user) {
            settingsDAO.deleteUserFromSetting(setting);
        }
        if (user.getSetting() == setting) {
            userDAO.deleteSettingFromUser(user);
        }
    }

}
