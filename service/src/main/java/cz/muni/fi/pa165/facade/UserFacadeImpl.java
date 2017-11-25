package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;
import cz.muni.fi.pa165.dto.enums.*;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.*;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/*
@author Martin Miškeje
*/
@Service
@Transactional
public class UserFacadeImpl implements UserFacade{
    @Inject
    private UserService userService;
    @Inject
    private BeanMappingService beanMappingService;
    
    @Override
    public ResultWithData<UserDTO> authenticate(AuthenticationDataDTO authData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserOperationResult registerUser(UserDTO user, String password) {
        UserOperationResult result = new UserOperationResult();
        try {
            User userToCreate = beanMappingService.mapTo(user, User.class);
            List<UserOperationErrorCode> errors = beanMappingService.mapTo(userService.create(userToCreate, password), UserOperationErrorCode.class);
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(userToCreate, UserDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapTo(e, UserOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(UserOperationErrorCode.UNKNOWN_ERROR);
            // todo log
        }
        return result;
    }

    @Override
    public UserOperationResult changePassword(UserDTO user, String oldPassword, String newPassword) {
        UserOperationResult result = new UserOperationResult();
        try {
            User userToUpdate = beanMappingService.mapTo(user, User.class);
            List<UserOperationErrorCode> errors = beanMappingService.mapTo(userService.changePassword(userToUpdate, oldPassword, newPassword), UserOperationErrorCode.class);
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(userToUpdate, UserDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapTo(e, UserOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(UserOperationErrorCode.UNKNOWN_ERROR);
            // todo log
        }
        return result;
    }

    @Override
    public UserOperationResult updateUser(UserDTO user) {
        UserOperationResult result = new UserOperationResult();
        try {
            User userToUpdate = beanMappingService.mapTo(user, User.class);
            List<UserOperationErrorCode> errors = beanMappingService.mapTo(userService.update(userToUpdate), UserOperationErrorCode.class);
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(userToUpdate, UserDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapTo(e, UserOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(UserOperationErrorCode.UNKNOWN_ERROR);
            // todo log
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
            // todo log
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
            // todo log
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
            // todo log
        }
        return result;
    }

}
