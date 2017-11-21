package cz.muni.fi.pa165.contracts;

/**
 *
 * @author Martin Miskeje
 */
public class CreateUserRequest extends RequestBaseWithData<User> {
    private String password = null;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}