package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author Matej Kralik
 */

@Entity
@Table(name="USERS") //user is reserved keyword
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userName;

    @NotNull
    @Size(min = 8)
    @Column(nullable=false)
    private String password;

    @NotNull
    @Column(nullable=false)
    private String name;

    @NotNull
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @NotNull
    @Column(nullable=false,unique=true)
    private LocalDateTime creationDate;

    private LocalDateTime activationDate;

    private LocalDateTime modificationDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        if (getType() != user.getType()) return false;
        return getCreationDate() != null ? getCreationDate().equals(user.getCreationDate()) : user.getCreationDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getPassword() != null ? getPassword().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        return result;
    }
}
