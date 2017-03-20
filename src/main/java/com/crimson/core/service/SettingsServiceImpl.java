package com.crimson.core.service;

import com.crimson.core.dao.SettingsDAO;
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
    SettingsDAO settingsDAO;

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
        settingsDAO.addUser2Setting(user, setting);
    }

    @Override
    public void deleteUserFromSetting(User user, Setting setting) {
        settingsDAO.deleteUserFromSetting(setting);
    }

}
