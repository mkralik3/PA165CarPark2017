package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.service.enums.*;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Martin Miskeje
 */
public interface UserService {
    Set<UserOperationErrorCode> create(User user, String password);
    Set<UserOperationErrorCode> update(User user);
    /*
    Returns deleted entity.
    */
    User delete(long userId);
    User findByUsername(String username);
    List<User> getAll();
}
