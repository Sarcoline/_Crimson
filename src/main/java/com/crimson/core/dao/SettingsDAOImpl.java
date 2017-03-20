package com.crimson.core.dao;

import com.crimson.core.model.Setting;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SettingsDAOImpl implements SettingsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(setting);
    }

    @Override
    @Cacheable("myCache")
    public List<Setting> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Setting a", Setting.class).getResultList();
    }

    @Override
    @Cacheable("myCache")
    public Setting getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Setting.class, id);
    }

    @Override
    public void delete(Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(setting);
    }

    @Override
    public void update(Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        session.update(setting);
    }

    //RELATIONSHIPS


    //User2Setting
    @Override
    public void addUser2Setting(User user, Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        setting.setUser(user);
        session.saveOrUpdate(setting);
    }

    @Override
    public void deleteUserFromSetting(User user, Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        if (setting.getUser() == user) {
            setting.setUser(null);
        }
        session.saveOrUpdate(setting);
    }
}
