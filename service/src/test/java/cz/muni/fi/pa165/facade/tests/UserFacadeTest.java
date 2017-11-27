package cz.muni.fi.pa165.facade.tests;

/**
 *
 * @author Tomas Pavuk
 */

import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.results.SimpleResult;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.tests.support.TestObjectFactory;
import java.util.Arrays;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserFacadeTest extends BaseFacadeTest {
    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private UserFacade userFacade;

    private User user1;
    private User user2;

    private UserDTO userDTO;

    private final String userName = "testUserName";
    
    private final TestObjectFactory factory = new TestObjectFactory();

    @BeforeMethod()
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userFacade = (UserFacade) unwrapProxy(userFacade);
        ReflectionTestUtils.setField(userFacade, "userService", userService);
        ReflectionTestUtils.setField(userFacade, "beanMappingService", beanMappingService);

        user1 = factory.createUser(userName, UserType.USER);
        user1.setPassword("password123");
        user2 = factory.createUser("TestUser2", UserType.USER);

        userDTO = new UserDTO();
        userDTO.setUserName("TestUser");
    }

    @Test
    public void testRegisterUser(){
        when(beanMappingService.mapTo(userDTO, User.class)).thenReturn(user1);

        userFacade.registerUser(userDTO, "testPassword");

        verify(userService).create(user1, "testPassword");
        verify(beanMappingService).mapTo(userDTO, User.class);
    }

    @Test
    public void testUpdateUser(){
        UserDTO userUpdatedDTO = userDTO;
        userUpdatedDTO.setUserName("UpdatedTest");
        User userUpdated = user1;
        userUpdated.setUserName("UpdatedTest");

        when(beanMappingService.mapTo(userDTO, User.class)).thenReturn(userUpdated);

        userFacade.updateUser(userUpdatedDTO);

        verify(userService).update(userUpdated);
        verify(beanMappingService).mapTo(userDTO, User.class);
    }
    
    @Test
    public void testChangePassword(){
        when(beanMappingService.mapTo(userDTO, User.class)).thenReturn(user1);

        userFacade.changePassword(userDTO, user1.getPassword(), "changedPassword");

        verify(userService).changePassword(user1, user1.getPassword(), "changedPassword");
        verify(beanMappingService).mapTo(userDTO, User.class);
    }

    @Test
    public void testFindAllUsers(){
        when(userService.getAll()).thenReturn(Arrays.asList(user1, user2));
        List<UserDTO> users = userFacade.findAllUsers();

        verify(userService).getAll();
        verify(beanMappingService).mapTo(user1, UserDTO.class);
        verify(beanMappingService).mapTo(user2, UserDTO.class);
    }

    @Test
    public void testFindUserByUsername(){
        when(userService.findByUsername(userName)).thenReturn(user1);

        UserDTO userDTO = userFacade.findUserByUserName(userName);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUserName()).isEqualTo(user1.getUserName());
        verify(userService).findByUsername(userName);
        verify(beanMappingService).mapTo(user1, UserDTO.class);
    }
    
    @Test
    public void testDeleteUser(){
        when(userService.delete(userDTO.getId())).thenReturn(user1);
        SimpleResult res = userFacade.deleteUser(userDTO.getId());

        verify(userService).delete(user1.getId());
        assertThat(res.getIsSuccess()).isEqualTo(true);
    }
}

