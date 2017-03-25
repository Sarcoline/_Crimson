package com.crimson.core.dao;

import com.crimson.core.model.PasswordResetToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Meow on 24.03.2017.
 */
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
    public void update(PasswordResetToken object) {

    }

    @Override
    public List<PasswordResetToken> getAll() {
        return null;
    }

    @Override
    public PasswordResetToken getById(Long key) {
        return null;
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From PasswordResetToken a where a.token like :custToken", PasswordResetToken.class)
                .setParameter("custToken", token).getSingleResult();
    }
}
