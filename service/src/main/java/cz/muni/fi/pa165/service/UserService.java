package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.service.errors.UserErrorCode;
import cz.muni.fi.pa165.entity.*;
import java.util.Set;

/**
 *
 * @author Martin Miskeje
 */
public interface UserService {
    Set<UserErrorCode> create(User user, String password);
}
