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

    //Saving passwordResetToken to database
    @Override
    public void save(PasswordResetToken token) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(token);
    }

    //Deleting passwordResetToken from database
    @Override
    public void delete(PasswordResetToken token) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(token);
    }

    //Updating passwordResetToken from database
    @Override
    public void update(PasswordResetToken token) {
        Session session = sessionFactory.getCurrentSession();
        session.update(token);
    }

    //Getting PasswordResetToken objects from database and returning list
    @Override
    public List<PasswordResetToken> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM PasswordResetToken a", PasswordResetToken.class).getResultList();
    }

    //Getting passwordResetToken from database by id
    @Override
    public PasswordResetToken getById(Long key) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(PasswordResetToken.class, key);
    }

    //Method to find passwordResetToken from database by token
    @Override
    public PasswordResetToken findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From PasswordResetToken a where a.token like :custToken", PasswordResetToken.class)
                .setParameter("custToken", token).getSingleResult();
    }
}
