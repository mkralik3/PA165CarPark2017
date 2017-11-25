package cz.muni.fi.pa165.facade.tests;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.enums.UserType;
import cz.muni.fi.pa165.dto.results.*;
import cz.muni.fi.pa165.facade.UserFacade;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.Test;

/**
 *
 * @author Martin Miskeje
 */
public class UserFacadeTests extends TestBase {
    @Inject
    private UserFacade userFacade;
    
    @Test
    public void registerUserTest(){
        UserDTO user = new UserDTO();
        user.setType(UserType.USER);
        user.setUserName("registerUserTest");
        UserOperationResult result = userFacade.registerUser(user, "test_password_123");
        assertThat(result).isNotNull();
        assertThat(result.getIsSuccess()).isTrue();
        assertThat(result.getData()).isNotNull();
    }
}
