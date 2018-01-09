package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;

import java.util.List;
/*
@author Martin Miškeje
*/
public interface UserFacade {

    /**
     * Register user
     * @param user user which will be registered
     * @param password user's password
     * @return operation with data
     * @throws IllegalArgumentException if user is null
     */
    UserOperationResult registerUser(UserDTO user, String password);

    /**
     * Change password for particular user
     * @param user user which will be updated
     * @param oldPassword old password
     * @param newPassword new password
     * @return operation with data
     * @throws IllegalArgumentException if user is null or it is not exist
     */
    UserOperationResult changePassword(UserDTO user, String oldPassword, String newPassword);

    /**
     * Update user
     * @param user user which will be updated
     * @return operation with data
     * @throws IllegalArgumentException if user is null or it is not exist
     */
    UserOperationResult updateUser(UserDTO user);

    /**
     * Delete user
     * @param userId id of user
     * @return simple result (isSuccess = true if found and deleted, otherwise false)
     */
    SimpleResult deleteUser(long userId);

    /**
     * Find all users
     * @return all users
     */
    List<UserDTO> findAllUsers();

    /**
     * Find particular user
     * @param userName userName of the user
     * @return particular user
     */
    UserDTO findUserByUserName(String userName);

    /**
     * Auth user
     * @param user - user for auth
     * @return result, particular user (UserDTO) whether operation was successful otherwise set of error codes
     */
    UserOperationResult authenticate(UserAuthDTO user);
}
