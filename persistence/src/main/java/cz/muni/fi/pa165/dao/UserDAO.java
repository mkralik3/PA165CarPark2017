package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface for User entity
 * @author Matej Kralik
 */
public interface UserDAO extends CrudRepository<User, Long>{

    /**
     * Find all users
     * @return all users
     */
    List<User> findAll();

    /**
     * Find user by user name
     * @param userName - user name for particular user
     * @return particular user or null when user with user name doesn't exist
     */
    User findUserByUserName(String userName);

    /**
     * Find user by user type
     * @param type - type of user
     * @return particular user or null when user with user type doesn't exist
     */
    List<User> findUsersByType(UserType type);
}
