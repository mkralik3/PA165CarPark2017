/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao.tests;

import cz.muni.fi.pa165.dao.tests.support.TestObjectFactory;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.UserType;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import static org.assertj.core.api.Assertions.assertThat;
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
        userDao.save(user);
        
        assertThat(user.getId()).isGreaterThan(0);
    }
    
    @Test(dependsOnMethods = "createUser")
    public void findAllUsers() {
        List<User> usersToCreate = new ArrayList<User>();
        usersToCreate.add(objectFactory.createUser("User1", "1234567890", UserType.USER));
        usersToCreate.add(objectFactory.createUser("User2", "1234567890", UserType.BRANCH_MANAGER));

        for (User user : usersToCreate){
            userDao.save(user);
        }

        List<User> foundUsers = (List<User>) userDao.findAll();
        
        for (User user : usersToCreate){
            assertThat(foundUsers).contains(user);
        }
    }

    @Test(dependsOnMethods = "createUser")
    public void findUserByUserName() {
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.save(user);

        User foundUser = userDao.findUserByUserName(user.getUserName());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo(user.getUserName());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(foundUser.getType()).isEqualTo(user.getType());
        assertThat(foundUser.getCreationDate()).isEqualTo(user.getCreationDate());
        assertThat(foundUser.getActivationDate()).isEqualTo(user.getActivationDate());
        assertThat(foundUser.getModificationDate()).isEqualTo(user.getModificationDate());
    }

    @Test(dependsOnMethods = "createUser")
    public void findUserByUserID() {
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.save(user);

        User foundUser = userDao.findOne(user.getId());

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
        userDao.save(user);
    }
    
    @Test(expectedExceptions = JpaSystemException.class, dependsOnMethods = "createUser")
    public void nonUniqueNameIsNotAllowed() {
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        User userWithSameName = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.save(user);
        userDao.save(userWithSameName);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullPasswordIsNotAllowed() {
        User user = objectFactory.createUser("User", null, UserType.USER);
        userDao.save(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void tooShortPasswordIsNotAllowed() {
        User user = objectFactory.createUser("User", "123", UserType.USER);
        userDao.save(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullUserTypeIsNotAllowed() {
        User user = objectFactory.createUser("User", "1234567890", null);
        userDao.save(user);
    }

    @Test(dependsOnMethods = {"createUser", "findUserByUserID"})
    public void updateUser() {
        User userToCreate = objectFactory.createUser("User", "1234567890", UserType.USER);
        User userToUpdate = objectFactory.createUser("ChangedUser", "1234567890", UserType.USER);
        userDao.save(userToCreate);
        
        userToUpdate.setId(userToCreate.getId());
        userDao.save(userToUpdate);

        User foundUser = userDao.findOne(userToUpdate.getId());
        assertThat(foundUser.getUserName()).isEqualTo(userToUpdate.getUserName());
    }

    @Test(dependsOnMethods = {"createUser", "findUserByUserID"})
    public void deleteUser() {
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.save(user);
        assertThat(userDao.findOne(user.getId())).isNotNull();
        userDao.delete(user);
        assertThat(userDao.findOne(user.getId())).isNull();
    }

    @Test(dependsOnMethods = "createUser")
    public void findUserByUserType(){
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.save(user);
        User user2 = objectFactory.createUser("User2", "1234567890", UserType.USER);
        userDao.save(user2);
        User user3 = objectFactory.createUser("User3", "1234567890", UserType.ADMIN);
        userDao.save(user3);

        List<User> foundedUsers = userDao.findUsersByType(UserType.USER);
        assertThat(foundedUsers).isNotNull();
        assertThat(foundedUsers).hasSize(2);
        assertThat(foundedUsers).contains(user,user2);
    }
}
