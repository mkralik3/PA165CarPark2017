package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;
import cz.muni.fi.pa165.dto.enums.*;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/*
@author Martin Miškeje
*/
@Service
@Transactional
public class UserFacadeImpl implements UserFacade{

    private final Logger log = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Inject
    private UserService userService;
    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public UserOperationResult registerUser(UserDTO user, String password) {
        UserOperationResult result = new UserOperationResult();
        try {
            User userToCreate = beanMappingService.mapTo(user, User.class);
            Set<UserOperationErrorCode> errors = new HashSet<>();
            userService.create(userToCreate, password).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, UserOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(userToCreate, UserDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, UserOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(UserOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public UserOperationResult changePassword(UserDTO user, String oldPassword, String newPassword) {
        UserOperationResult result = new UserOperationResult();
        try {
            User userToUpdate = beanMappingService.mapTo(user, User.class);
            Set<UserOperationErrorCode> errors = new HashSet<>();
            userService.changePassword(userToUpdate, oldPassword, newPassword).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, UserOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(userToUpdate, UserDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, UserOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(UserOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public UserOperationResult updateUser(UserDTO user) {
        UserOperationResult result = new UserOperationResult();
        try {
            User userToUpdate = beanMappingService.mapTo(user, User.class);
            Set<UserOperationErrorCode> errors = new HashSet<>();
            userService.update(userToUpdate).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, UserOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(userToUpdate, UserDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, UserOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(UserOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public SimpleResult deleteUser(long userId) {
        SimpleResult result = new SimpleResult();
        try {
            User deletedUser = userService.delete(userId);
            result.setIsSuccess(deletedUser != null);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserDTO> result = new ArrayList<>();
        try {
            List<User> users = userService.getAll();
            if (users != null) {
                users.forEach((u) -> {
                    result.add(beanMappingService.mapTo(u, UserDTO.class));
                });
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public UserDTO findUserByUserName(String userName) {
        UserDTO result = null;
        try {
            User user = userService.findByUsername(userName);
            if (user != null) {
                result = beanMappingService.mapTo(user, UserDTO.class);
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public UserOperationResult authenticate(UserAuthDTO user) {
        UserOperationResult result = new UserOperationResult();
        try {
            Set<UserOperationErrorCode> errors = new HashSet<>();
            userService.authenticate(user.getUserName(),user.getPassword()).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, UserOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
                User authorizedUser = userService.findByUsername(user.getUserName());
                result.setData(beanMappingService.mapTo(authorizedUser, UserDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, UserOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(UserOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

}
