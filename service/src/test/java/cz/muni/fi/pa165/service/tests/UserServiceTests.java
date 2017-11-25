package cz.muni.fi.pa165.service.tests;

import cz.muni.fi.pa165.service.UserService;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.Test;

/**
 *
 * @author Martin Miskeje
 */
public class UserServiceTests extends TestBase {
    @Inject
    private UserService userService;
    
    @Test
    public void createTest(){
        
    }
}
