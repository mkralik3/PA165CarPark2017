/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import java.util.Collection;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Matej Kralik
 */
@Repository
public class UserDAOImpl implements UserDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createUser(User user) {
        em.persist(user);
    }

    @Override
    public User updateUser(User user) {
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

    @Override
    public Collection<User> findUsersByType(UserType type) {
        return em.createQuery("SELECT u FROM User u WHERE u.userType = :type", User.class) //USERS
                .setParameter("type", type)
                .getResultList();
    }
}
