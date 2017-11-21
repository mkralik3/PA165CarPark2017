package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;

import java.util.List;

public class UserFacadeImpl implements UserFacade{
    @Override
    public void registerUser(UserDTO user, String unHashPassword) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void changePassword(UserDTO user, String newUnHashPassword) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateUser(UserDTO user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteUser(long id) {

        throw new UnsupportedOperationException("Not supported yet.");    }

    @Override
    public List<CarDTO> findAllUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserDTO findUserByUserName(String userName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isAdmin(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RegionalBranchDTO isManager(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
