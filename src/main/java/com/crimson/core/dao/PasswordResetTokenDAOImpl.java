package com.crimson.core.dao;

import com.crimson.core.model.PasswordResetToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PasswordResetTokenDAOImpl implements PasswordResetTokenDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(PasswordResetToken token) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(token);
    }

    @Override
    public void delete(PasswordResetToken token) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(token);
    }

    @Override
    public void update(PasswordResetToken token) {
        Session session = sessionFactory.getCurrentSession();
        session.update(token);
    }

    @Override
    public List<PasswordResetToken> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM PasswordResetToken a", PasswordResetToken.class).getResultList();
    }

    @Override
    public PasswordResetToken getById(Long key) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(PasswordResetToken.class, key);
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From PasswordResetToken a where a.token like :custToken", PasswordResetToken.class)
                .setParameter("custToken", token).getSingleResult();
    }
}
