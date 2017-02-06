package com.crimson.core.service;

import com.crimson.core.dao.SettingsDAO;
import com.crimson.core.model.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SettingsServiceImpl implements  SettingsService{

    @Autowired
    SettingsDAO settingsDAO;

    @Override
    public void saveSetting(Setting setting) {
        settingsDAO.saveSetting(setting);
    }

    @Override
    public List<Setting> getAllSettings() {
        return settingsDAO.getAllSettings();
    }

    @Override
    public Setting getSettingById(Long id) {
        return settingsDAO.getSettingById(id);
    }

    @Override
    public void deleteSetting(Setting setting) {
        settingsDAO.deleteSetting(setting);
    }

    @Override
    public void updateSetting(Setting setting) {
        settingsDAO.updateSetting(setting);
    }
}
