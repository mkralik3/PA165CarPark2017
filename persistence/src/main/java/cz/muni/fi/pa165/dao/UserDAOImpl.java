/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.DateTimeProvider;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import java.util.Collection;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Matej Kralik
 */
@Repository
public class UserDAOImpl implements UserDAO{

    private final DateTimeProvider dateTimeProvider;
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    public UserDAOImpl(DateTimeProvider dateTimeProvider){
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public void createUser(User user) {
        if (user.getCreationDate() == null) user.setCreationDate(dateTimeProvider.provideNow());
        if (user.getModificationDate() == null) user.setModificationDate(user.getCreationDate());
        em.persist(user);
    }

    @Override
    public User updateUser(User user) {
        if (user.getModificationDate() == null) user.setModificationDate(dateTimeProvider.provideNow());
        return em.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }

    @Override
    public User findUserById(Long id) {
        return em.find(User.class,id);
    }

    @Override
    public User findUserByUserName(String userName) {
                return em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class) //USERS
                .setParameter("username", userName)
                .getSingleResult();
    }

    @Override
    public Collection<User> findAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }
}
