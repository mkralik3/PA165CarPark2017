/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao.tests;

import cz.muni.fi.pa165.dao.tests.support.TestObjectFactory;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaSystemException;
import org.testng.annotations.Test;

/**
 *
 * @author Tomas Pavuk
 */
public class UserDaoTest extends TestBase {

    private final TestObjectFactory objectFactory = new TestObjectFactory();
    
    @Test
    public void createUser() {
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.createUser(user);
        assertThat(user.getId()).isGreaterThan(0);
    }
    
    @Test
    public void findAllUsers() {
        List<User> usersToCreate = new ArrayList<User>();
        usersToCreate.add(objectFactory.createUser("User1", "1234567890", UserType.USER));
        usersToCreate.add(objectFactory.createUser("User2", "1234567890", UserType.BRANCH_MANAGER));

        for (User user : usersToCreate){
            userDao.createUser(user);
        }

        List<User> foundUsers = (List<User>) userDao.findAllUsers();
        
        for (User user : usersToCreate){
            assertThat(foundUsers).contains(user);
        }
    }

    @Test
    public void findUserByUserName() {
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.createUser(user);

        User foundUser = userDao.findUserByUserName(user.getUserName());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo(user.getUserName());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(foundUser.getType()).isEqualTo(user.getType());
        assertThat(foundUser.getCreationDate()).isEqualTo(user.getCreationDate());
        assertThat(foundUser.getActivationDate()).isEqualTo(user.getActivationDate());
        assertThat(foundUser.getModificationDate()).isEqualTo(user.getModificationDate());
    }

    @Test
    public void findUserByUserID() {
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.createUser(user);

        User foundUser = userDao.findUserById(user.getId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo(user.getUserName());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(foundUser.getType()).isEqualTo(user.getType());
        assertThat(foundUser.getCreationDate()).isEqualTo(user.getCreationDate());
        assertThat(foundUser.getActivationDate()).isEqualTo(user.getActivationDate());
        assertThat(foundUser.getModificationDate()).isEqualTo(user.getModificationDate());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullNameIsNotAllowed() {
        User user = objectFactory.createUser(null, "1234567890", UserType.USER);
        userDao.createUser(user);
    }
    
    @Test(expectedExceptions = JpaSystemException.class)
    public void nonUniqueNameIsNotAllowed() {
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        User userWithSameName = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.createUser(user);
        userDao.createUser(userWithSameName);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullPasswordIsNotAllowed() {
        User user = objectFactory.createUser("User", null, UserType.USER);
        userDao.createUser(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void tooShortPasswordIsNotAllowed() {
        User user = objectFactory.createUser("User", "123", UserType.USER);
        userDao.createUser(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullUserTypeIsNotAllowed() {
        User user = objectFactory.createUser("User", "1234567890", null);
        userDao.createUser(user);
    }

    @Test
    public void updateUser() {
        User userToCreate = objectFactory.createUser("User", "1234567890", UserType.USER);
        User userToUpdate = objectFactory.createUser("ChangedUser", "1234567890", UserType.USER);
        userDao.createUser(userToCreate);
        
        userToUpdate.setId(userToCreate.getId());
        userDao.updateUser(userToUpdate);

        User foundUser = userDao.findUserById(userToUpdate.getId());
        assertThat(foundUser.getUserName()).isEqualTo(userToUpdate.getUserName());
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteNullUserIsNotAllowed() {
        userDao.deleteUser(null);
    }

    @Test()
    public void deleteUser() {
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.createUser(user);
        assertThat(userDao.findUserById(user.getId())).isNotNull();
        userDao.deleteUser(user);
        assertThat(userDao.findUserById(user.getId())).isNull();
    }
}
