package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.facade.CarReservationRequestFacadeImpl;
import cz.muni.fi.pa165.service.enums.UserOperationErrorCode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Miskeje
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final int MIN_PASSWORD_LENGTH = 6;
    @Inject
    private UserDAO userDao;
    @Inject
    private TimeService timeService;
    @Inject
    private PasswordSupportService passwordSupport;
    
    @Override
    public Set<UserOperationErrorCode> create(User user, String password) {
        log.info("User will be created: " + user);
        if (user == null) {
            log.error("user argument is null");
            throw new IllegalArgumentException("user is null");
        }
        Set<UserOperationErrorCode> errors = new HashSet<>();
        if (password == null) {
            log.debug("user has not got password");
            errors.add(UserOperationErrorCode.PASSWORD_REQUIRED);
        }
        else if (password.length() < MIN_PASSWORD_LENGTH) {
            log.debug("password is not big enough");
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
                user.setPassword(null);
            } catch (DataAccessException ex) {
                errors.add(UserOperationErrorCode.DATABASE_ERROR);
                log.error(ex.toString());
            }
        }
        return errors;
    }

    @Override
    public Set<UserOperationErrorCode> update(User user) {
        log.info("User will be updated: " + user);
        if (user == null) {
            log.error("user argument is null");
            throw new IllegalArgumentException("user is null");
        }
        // get user from db for change safety
        User existingUser = userDao.findOne(user.getId());
        if (existingUser == null) {
            log.error("user not exists");
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
            log.debug("username required!");
        }
        if (errors.isEmpty()) {
            if (existingUser == null || !newUser.getUserName().equals(existingUser.getUserName())) {
                if (userDao.findUserByUserName(newUser.getUserName()) != null) {
                    errors.add(UserOperationErrorCode.USERNAME_EXISTS);
                    log.debug("username exists!");
                }
            }
        }
        return errors;
    }

    @Override
    public User delete(long userId) {
        log.info("User with id " + userId + "  will be deleted");
        User user = userDao.findOne(userId);
        if (user != null) {
            userDao.delete(user);
        }else{
            log.debug("User is not exist!");
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

    @Override
    public Set<UserOperationErrorCode> changePassword(User user, String oldPassword, String newPassword) {
        if (user == null) {
            throw new NullPointerException("user");
        }
        // get user from db for change safety
        user = userDao.findOne(user.getId());
        if (user == null) {
            throw new IllegalArgumentException("user not exists");
        }
        Set<UserOperationErrorCode> errors = new HashSet<>();
        if (oldPassword == null || newPassword == null) {
            errors.add(UserOperationErrorCode.PASSWORD_REQUIRED);
        }
        else if (newPassword.length() < MIN_PASSWORD_LENGTH) {
            errors.add(UserOperationErrorCode.PASSWORD_LENGTH);
        }
        //else if (!passwordSupport.createHash(oldPassword).equals(user.getPassword())){
        else if (!passwordSupport.validatePassword(oldPassword, user.getPassword())){
            // possible improvement of security - count fail attempts
            errors.add(UserOperationErrorCode.PASSWORD_MISMATCH);
        }
        if (errors.isEmpty()) {
             user.setPassword(passwordSupport.createHash(newPassword));
             user.setModificationDate(timeService.getCurrentTime());
             userDao.save(user);
        }
        return errors;
    }

}
