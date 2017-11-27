/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.tests;

import cz.muni.fi.pa165.dao.UserDAO;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.service.PasswordSupportService;
import cz.muni.fi.pa165.service.PasswordSupportServiceImpl;
import cz.muni.fi.pa165.service.TimeService;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.service.UserServiceImpl;
import cz.muni.fi.pa165.service.enums.UserOperationErrorCode;
import cz.muni.fi.pa165.tests.support.TestObjectFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Tomas Pavuk
 */
public class UserServiceTest extends BaseServiceTest {
    @Mock
    private UserDAO userDao;
    
    @Mock
    private TimeService timeService;
    
    @Spy
    private final PasswordSupportService passwordSupport = new PasswordSupportServiceImpl();
    
    @InjectMocks
    private final UserService userService = new UserServiceImpl();
    
    private final TestObjectFactory objectFactory = new TestObjectFactory();
    private User user1;
    private User user2;
    
    private final String testUsername = "sampleUser";
    private final String testPassword = "testPassword";
    private final String hashedPassword = passwordSupport.createHash(testPassword);
    
    @BeforeMethod
    public void setup() throws ServiceException {
        user1 = objectFactory.createUser(testUsername, UserType.USER);
        user1.setPassword(hashedPassword);
        user2 = objectFactory.createUser("sampleUser2", UserType.BRANCH_MANAGER);
        
        when(userDao.findUserByUserName(user1.getUserName()))
            .thenReturn(user1);
        
        when(userDao.findUserByUserName(user2.getUserName()))
            .thenReturn(null);
        
        when(userDao.findOne(user1.getId()))
            .thenReturn(user1);
        
        when(userDao.findAll())
            .thenReturn(Arrays.asList(user1, user2));
        
        doAnswer((Answer<Object>) (InvocationOnMock invocation) -> {
            Object argument = invocation.getArguments()[0];
            if(argument == null) {
                throw new IllegalArgumentException();
            }
            
            User user = (User) argument;
            user.setId(Long.valueOf(1));
            return null;
        }).when(userDao).save(any(User.class));
        
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            Object argument = invocation.getArguments()[0];
            
            long userId = (long) argument;
            if(userId < 0){
                throw new IllegalArgumentException();
            }
            
            return null;
        }).when(userDao).delete(any(long.class));
    }
    
    @BeforeClass
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    
    @Test
    public void findUser() throws IllegalArgumentException{
        User user = userService.findByUsername(testUsername);
        
        assertThat(user).isNotNull();
        assertThat(user).isEqualToComparingFieldByField(user1);
    }
    
    @Test
    public void findNonExistingUser() throws IllegalArgumentException{
        User user = userService.findByUsername("nonexisting");
        assertThat(user).isNull();
    }
    
    @Test
    public void createUser() throws IllegalArgumentException{
        Set<UserOperationErrorCode> errors = userService.create(user2, testPassword);
        
        assertThat(errors).isEmpty();
        assertThat(user2.getId()).isGreaterThan(0);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullUser() throws NullPointerException{
        userService.create(null, "validPassword");
    }
    
    @Test
    public void createUserWithNullPassword() throws IllegalArgumentException{
        Set<UserOperationErrorCode> errors = userService.create(user1, null);
        
        assertThat(errors).isNotNull();
        assertThat(errors).contains(UserOperationErrorCode.PASSWORD_REQUIRED);
    }
    
    @Test
    public void createUserWithTooShortPassword() throws IllegalArgumentException{
        Set<UserOperationErrorCode> errors = userService.create(user1, "a");
        
        assertThat(errors).isNotNull();
        assertThat(errors).contains(UserOperationErrorCode.PASSWORD_LENGTH);
    }
    
    @Test
    public void changePassword() throws IllegalArgumentException{
        Set<UserOperationErrorCode> errors = userService.changePassword(user1, testPassword, "newPassword");
        
        assertThat(errors).isEmpty();
        passwordSupport.validatePassword("newPassword", user1.getPassword());
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void changePasswordForNullUser() throws NullPointerException{
        userService.changePassword(null, testPassword, "newPassword");
    }
    
    @Test
    public void changePasswordToNull() throws IllegalArgumentException{
        Set<UserOperationErrorCode> errors = userService.changePassword(user1, testPassword, null);
        
        assertThat(errors).isNotNull();
        assertThat(errors).contains(UserOperationErrorCode.PASSWORD_REQUIRED);
    }
    
    @Test
    public void changePasswordFromNull() throws IllegalArgumentException{
        Set<UserOperationErrorCode> errors = userService.changePassword(user1, null, "newPassword");
        
        assertThat(errors).isNotNull();
        assertThat(errors).contains(UserOperationErrorCode.PASSWORD_REQUIRED);
    }
    
    @Test
    public void changePasswordToTooShortPassword() throws IllegalArgumentException{
        Set<UserOperationErrorCode> errors = userService.changePassword(user1, testPassword, "a");
        
        assertThat(errors).isNotNull();
        assertThat(errors).contains(UserOperationErrorCode.PASSWORD_LENGTH);
    }
    
    @Test
    public void changePasswordInvalidOldPassword() throws IllegalArgumentException{
        Set<UserOperationErrorCode> errors = userService.changePassword(user1, "invalid", "testPassword");
        
        assertThat(errors).isNotNull();
        assertThat(errors).contains(UserOperationErrorCode.PASSWORD_MISMATCH);
    }
    
    @Test
    public void findAllUsers() {
        List<User> users = userService.getAll();
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users).contains(user1, user2);
    }
    
    @Test
    public void updateUser() throws IllegalArgumentException{
        user1.setUserName("UpdatedName");
        userService.update(user1);
        assertThat(user1.getUserName()).isEqualTo("UpdatedName");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullUser() {
        userService.update(null);
    }
    
    @Test
    public void deleteNonExistingUser(){
        User deletedUser = userService.delete(-1L);
        assertThat(deletedUser).isNull();
    }
    
    @Test
    public void deleteUser() throws IllegalArgumentException{
        User deletedUser = userService.delete(user1.getId());
        Mockito.verify(userDao, Mockito.times(1)).delete(user1);
        
        assertThat(deletedUser).isEqualToComparingFieldByField(user1);
    }
}
