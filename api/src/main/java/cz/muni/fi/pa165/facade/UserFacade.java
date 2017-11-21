package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;

import java.util.List;

public interface UserFacade {

    void registerUser(UserDTO user, String unHashPassword);

    void changePassword(UserDTO user, String newUnHashPassword);

    void updateUser(UserDTO user);

    void deleteUser(long id);

    List<CarDTO> findAllUser();

    UserDTO findUserByUserName(String userName);

    boolean isAdmin(long id);

    /*return null whether user is not manager of branch*/
    RegionalBranchDTO isManager(long id);


}
