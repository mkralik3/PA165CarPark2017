package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.enums.UserOperationErrorCode;
import java.util.HashSet;
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
        if (user.getUserName() == null) {
            errors.add(UserOperationErrorCode.USERNAME_REQUIRED);
        }
        if (password == null) {
            errors.add(UserOperationErrorCode.PASSWORD_REQUIRED);
        }
        else if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add(UserOperationErrorCode.PASSWORD_LENGTH);
        }
        if (errors.isEmpty()) {
            if (userDao.findUserByUserName(user.getUserName()) != null) {
                errors.add(UserOperationErrorCode.USERNAME_EXISTS);
            }
            else {
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
        }
        return errors;
    }

}
