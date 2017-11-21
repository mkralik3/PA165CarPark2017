package cz.muni.fi.pa165.contracts.request;

import cz.muni.fi.pa165.contracts.RequestBaseWithData;
import cz.muni.fi.pa165.contracts.UserDTO;

/**
 *
 * @author Martin Miskeje
 */
public class CreateUserRequest extends RequestBaseWithData<UserDTO> {
    private String password = null;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}