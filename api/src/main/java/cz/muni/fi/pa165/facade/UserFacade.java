package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;

import java.util.List;
/*
@author Martin Miškeje
*/
public interface UserFacade {
    
    UserOperationResult registerUser(UserDTO user, String password);

    UserOperationResult changePassword(UserDTO user, String oldPassword, String newPassword);

    /*
    Activation/deactivation will be through this method
    */
    UserOperationResult updateUser(UserDTO user);

    /*
    Returns isSuccess = true if found and deleted, otherwise false
    */
    SimpleResult deleteUser(long userId);

    List<UserDTO> findAllUsers();

    /*
    Returns user if exists, otherwise null
    */
    UserDTO findUserByUserName(String userName);
}
