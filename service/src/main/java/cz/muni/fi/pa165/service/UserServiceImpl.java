package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.enums.UserOperationErrorCode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Miskeje
 */
@Service
public class UserServiceImpl implements UserService {
    private final int MIN_PASSWORD_LENGTH = 6;
    @Inject
    private UserDAO userDao;
    @Inject
    private TimeService timeService;
    @Inject
    private PasswordSupportService passwordSupport;
    
    @Override
    public Set<UserOperationErrorCode> create(User user, String password) {
        if (user == null) {
            throw new NullPointerException("user");
        }
        Set<UserOperationErrorCode> errors = new HashSet<>();
        if (password == null) {
            errors.add(UserOperationErrorCode.PASSWORD_REQUIRED);
        }
        else if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add(UserOperationErrorCode.PASSWORD_LENGTH);
        }
        errors.addAll(validateUserInput(user, null));
        if (errors.isEmpty()) {
            user.setPassword(passwordSupport.createHash(password));
            try {
                user.setActivationDate(timeService.getCurrentTime());
                user.setCreationDate(timeService.getCurrentTime());
                user.setModificationDate(timeService.getCurrentTime());
                userDao.save(user);
            }
            catch (DataAccessException ex) {
                errors.add(UserOperationErrorCode.DATABASE_ERROR);
                // todo log
            }
        }
        return errors;
    }

    @Override
    public Set<UserOperationErrorCode> update(User user) {
        if (user == null) {
            throw new NullPointerException("user");
        }
        User existingUser = userDao.findUserByUserName(user.getUserName());
        if (existingUser == null) {
            throw new IllegalArgumentException("user not exists");
        }
        Set<UserOperationErrorCode> errors = new HashSet<>();
        errors.addAll(validateUserInput(user, existingUser));
        if (errors.isEmpty()) {
            //change is permitted only for this properties:
            existingUser.setType(user.getType());
            existingUser.setUserName(user.getUserName());
            existingUser.setModificationDate(timeService.getCurrentTime());
            userDao.save(existingUser);
        }
        return errors;
    }
    
    private Set<UserOperationErrorCode> validateUserInput(User newUser, User existingUser){
        Set<UserOperationErrorCode> errors = new HashSet<>();
        if (newUser.getUserName() == null) {
            errors.add(UserOperationErrorCode.USERNAME_REQUIRED);
        }
        if (errors.isEmpty()) {
            if (existingUser == null || !newUser.getUserName().equals(existingUser.getUserName())) {
                if (userDao.findUserByUserName(newUser.getUserName()) != null) {
                    errors.add(UserOperationErrorCode.USERNAME_EXISTS);
                }
            }
        }
        return errors;
    }

    @Override
    public User delete(long userId) {
        User user = userDao.findOne(userId);
        if (user != null) {
            userDao.delete(user);
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findUserByUserName(username);
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

}
