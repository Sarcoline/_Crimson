package com.crimson.core.dao;

import com.crimson.core.model.Setting;
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
    @Cacheable("application-cache")
    public List<Setting> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Setting a", Setting.class).getResultList();
    }

    @Override
    @Cacheable("application-cache")
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
}
