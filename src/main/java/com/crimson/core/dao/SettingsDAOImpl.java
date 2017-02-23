package com.crimson.core.dao;

import com.crimson.core.model.Setting;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SettingsDAOImpl implements SettingsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveSetting(Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(setting);
    }

    @Override
    public List<Setting> getAllSettings() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Setting a", Setting.class).getResultList();
    }

    @Override
    public Setting getSettingById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Setting.class, id);
    }

    @Override
    public void deleteSetting(Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(setting);
    }

    @Override
    public void updateSetting(Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        session.update(setting);
    }

    //RELATIONSHIPS


    //User2Setting
    @Override
    public void addUser2Setting(User user, Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        setting.setUser2Setting(user);
        session.saveOrUpdate(setting);
    }

    @Override
    public void deleteUserFromSetting(User user, Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        if (setting.getUser2Setting() == user) {
            setting.setUser2Setting(null);
        }
        session.saveOrUpdate(session);
    }
}
