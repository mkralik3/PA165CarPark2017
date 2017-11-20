package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.contracts.*;

/**
 *
 * @author Martin Miskeje
 */
public interface UserFacade {
    CreateUserResponse CreateUser(CreateUserRequest request);
    /*
        Update is without password change. Changing of password should be possible through special workflow.
    */
    UpdateUserResponse UpdateUser(UpdateUserRequest request);
    DeleteUserResponse DeleteUser(DeleteUserRequest request);
    // get all - should be here paging? for milestone 2 don´t have to be...
}
