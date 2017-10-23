package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;

import java.util.Collection;

/**
 * The interface for User entity
 * @author Matej Kralik
 */
public interface UserDAO {
    /**
     * Create user in database
     * @param user - particular user
     */
    void createUser(User user);

    /**
     * Update particular user from database
     * @param user - particular user
     * @return updated user
     */
    User updateUser(User user);

    /**
     * Delete particular user from database
     * @param user - particular user
     */
    void deleteUser(User user);

    /**
     * Find user by user name
     * @param userName - user name for particular user
     * @return particular user or null when user with user name doesn't exist
     */
    User findUserByUserName(String userName);

    /**
     * Find all users in the database
     * @return Collection of all users from database
     */
    Collection<User> findAllUsers();

    /**
     * Find all users according to type
     * @param type - type of user
     * @return Collection with all users with same type
     */
    Collection<User> findUsersByType(UserType type);

}
