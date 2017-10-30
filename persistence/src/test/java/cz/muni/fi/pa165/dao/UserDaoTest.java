/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Tomas Pavuk
 */
public class UserDaoTest extends AbstractDao {

    @Test
    public void findAllUsers() {
        User user1 = new User();
        User user2 = new User();
        user1.setUserName("User1");
        user1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        user1.setPassword("1234567890");
        user1.setType(UserType.USER);

        user2.setUserName("User2");
        user2.setCreationDate(LocalDateTime.of(2012, Month.MARCH, 20, 10, 10));
        user2.setPassword("1234567890");
        user2.setType(UserType.BRANCH_MANAGER);

        userDao.createUser(user1);
        userDao.createUser(user2);

        List<User> users = (List<User>) userDao.findAllUsers();

        assertThat(users.size()).isEqualTo(2);
        assertThat(users).contains(user1);
        assertThat(users).contains(user2);
    }

    @Test
    public void findUserByUserName() {
        User user = new User();
        user.setUserName("User");
        user.setPassword("1234567890");
        user.setType(UserType.BRANCH_MANAGER);
        user.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        userDao.createUser(user);

        User foundUser = userDao.findUserByUserName(user.getUserName());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo("User");
    }

    @Test
    public void findUserByUserID() {
        User user = new User();
        user.setUserName("User");
        user.setPassword("1234567890");
        user.setType(UserType.BRANCH_MANAGER);
        user.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        userDao.createUser(user);

        User foundUser = userDao.findUserById(user.getId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo("User");
    }

    @Test
    public void createUsersSavesUserProperties() {
        User user = new User();
        user.setUserName("User");
        user.setPassword("1234567890");
        user.setType(UserType.BRANCH_MANAGER);
        user.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        user.setActivationDate(LocalDateTime.of(2017, Month.APRIL, 20, 10, 10));
        user.setModificationDate(LocalDateTime.of(2017, Month.MAY, 20, 10, 10));
        userDao.createUser(user);

        User foundUser = userDao.findUserById(user.getId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo("User");
        assertThat(foundUser.getPassword()).isEqualTo("1234567890");
        assertThat(foundUser.getType()).isEqualTo(UserType.BRANCH_MANAGER);
        assertThat(foundUser.getCreationDate()).isEqualTo(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        assertThat(foundUser.getActivationDate()).isEqualTo(LocalDateTime.of(2017, Month.APRIL, 20, 10, 10));
        assertThat(foundUser.getModificationDate()).isEqualTo(LocalDateTime.of(2017, Month.MAY, 20, 10, 10));
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullNameIsNotAllowed() {
        User user = new User();
        user.setUserName(null);
        user.setPassword("1234567890");
        user.setType(UserType.BRANCH_MANAGER);
        user.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        userDao.createUser(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullPasswordIsNotAllowed() {
        User user = new User();
        user.setUserName("User");
        user.setPassword(null);
        user.setType(UserType.BRANCH_MANAGER);
        user.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        userDao.createUser(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void tooShortPasswordIsNotAllowed() {
        User user = new User();
        user.setUserName("User");
        user.setPassword("123");
        user.setType(UserType.BRANCH_MANAGER);
        user.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        userDao.createUser(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullUserTypeIsNotAllowed() {
        User user = new User();
        user.setUserName("User");
        user.setPassword("1234567890");
        user.setType(null);
        user.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        userDao.createUser(user);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setUserName("User");
        user.setPassword("1234567890");
        user.setType(UserType.BRANCH_MANAGER);
        user.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        userDao.createUser(user);

        user.setUserName("UserWithChangedName");
        user.setCreationDate(LocalDateTime.of(2016, Month.FEBRUARY, 20, 10, 10));

        User foundUser = userDao.findUserById(user.getId());

        assertThat(foundUser.getUserName()).isEqualTo("UserWithChangedName");
        assertThat(foundUser.getCreationDate()).isEqualTo(LocalDateTime.of(2016, Month.FEBRUARY, 20, 10, 10));
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteNullUserIsNotAllowed() {
        userDao.deleteUser(null);
    }

    @Test()
    public void deleteUser() {
        User user = new User();
        user.setUserName("User");
        user.setPassword("1234567890");
        user.setType(UserType.BRANCH_MANAGER);
        user.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        userDao.createUser(user);
        assertThat(userDao.findUserById(user.getId())).isNotNull();
        userDao.deleteUser(user);
        assertThat(userDao.findUserById(user.getId())).isNull();
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void updateNullUserIsNotAllowed() {
        userDao.updateUser(null);
    }
}
