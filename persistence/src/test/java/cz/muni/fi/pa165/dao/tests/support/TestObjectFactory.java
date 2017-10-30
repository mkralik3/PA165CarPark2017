package cz.muni.fi.pa165.dao.tests.support;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import java.time.LocalDateTime;
import java.time.Month;

/**
 *
 * @author Martin Miškeje
 */
public class TestObjectFactory {
    
    public Car createCar(String name){
        Car result = new Car();
        result.setName(name);
        return result;
    }
    
    public User createUser(String username, String password, UserType type) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setType(type);
        return user;
    }
}
