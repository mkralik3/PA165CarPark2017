package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.contracts.UserDTO;
import cz.muni.fi.pa165.contracts.request.CreateUserRequest;
import cz.muni.fi.pa165.contracts.request.DeleteUserRequest;
import cz.muni.fi.pa165.contracts.request.UpdateUserRequest;
import cz.muni.fi.pa165.contracts.response.CreateUserResponse;
import cz.muni.fi.pa165.contracts.response.DeleteUserResponse;
import cz.muni.fi.pa165.contracts.response.UpdateUserResponse;

import java.util.List;

/**
 *
 * @author Martin Miskeje
 */
public interface UserFacade {
    CreateUserResponse createUser(CreateUserRequest request);
    /*
        Update is without password change. Changing of password should be possible through special workflow.
    */
    UpdateUserResponse updateUser(UpdateUserRequest request);
    DeleteUserResponse deleteUser(DeleteUserRequest request);
    // get all - should be here paging? for milestone 2 don�t have to be...
    boolean isAdmin(UserDTO userDTO);
    boolean isManager(UserDTO userDTO);
    List<UserDTO> findAll();
    UserDTO findUserByUserName(String userName);
}
