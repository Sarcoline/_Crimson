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

    //Saving Setting object to database
    @Override
    public void save(Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(setting);
    }

    //Getting Setting objects from database and returning list
    @Override
    @Cacheable("application-cache")
    public List<Setting> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Setting a", Setting.class).getResultList();
    }

    //Getting Setting object from database by id
    @Override
    @Cacheable("application-cache")
    public Setting getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Setting.class, id);
    }

    //Deleting Setting object from database
    @Override
    public void delete(Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(setting);
    }

    //Updating Setting object from database
    @Override
    public void update(Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        session.update(setting);
    }
}
