package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.UserType;

public class UserDTO {

    private long id;

    private String userName;

    private String password;

    private UserType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        UserDTO userDTO = (UserDTO) o;

        if (getUserName() != null ? !getUserName().equals(userDTO.getUserName()) : userDTO.getUserName() != null)
            return false;
        return getType() == userDTO.getType();
    }

    @Override
    public int hashCode() {
        int result = getUserName() != null ? getUserName().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }
}
