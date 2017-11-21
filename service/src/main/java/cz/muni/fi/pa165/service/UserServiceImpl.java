package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.service.errors.UserErrorCode;
import cz.muni.fi.pa165.dao.UserDAO;
import cz.muni.fi.pa165.entity.User;
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
    private PasswordSupportService passwordSupport;
    
    @Override
    public Set<UserErrorCode> create(User user, String password) {
        Set<UserErrorCode> errors = new HashSet<>();
        if (user.getUserName() == null) {
            errors.add(UserErrorCode.USERNAME_REQUIRED);
        }
        if (password == null) {
            errors.add(UserErrorCode.PASSWORD_REQUIRED);
        }
        else if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add(UserErrorCode.PASSWORD_LENGTH);
        }
        if (errors.isEmpty()) {
            if (userDao.findUserByUserName(user.getUserName()) != null) {
                errors.add(UserErrorCode.USERNAME_EXISTS);
            }
            else {
                user.setPassword(passwordSupport.createHash(password));
                try {
                    userDao.createUser(user);
                }
                catch (DataAccessException ex) {
                    errors.add(UserErrorCode.DATABASE_ERROR);
                    // todo log
                }
            }
        }
        return errors;
    }

}
