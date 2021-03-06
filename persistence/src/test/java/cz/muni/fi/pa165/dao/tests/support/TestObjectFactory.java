package cz.muni.fi.pa165.dao.tests.support;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import cz.muni.fi.pa165.enums.UserType;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Martin Mi�keje
 */
public class TestObjectFactory {

    private LocalDateTime currentTime = LocalDateTime.now();

    public Car createCar(String name) {
        Car result = new Car();
        result.setName(name);
        result.setCreationDate(currentTime);
        result.setModificationDate(currentTime);
        return result;
    }

    public User createUser(String username, String password, UserType type) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setCreationDate(currentTime);
        user.setModificationDate(currentTime);
        user.setType(type);
        return user;
    }

    public RegionalBranch createRegionalBranch(String name) {
        RegionalBranch branch = new RegionalBranch();
        branch.setName(name);
        branch.setCreationDate(currentTime);
        branch.setModificationDate(currentTime);
        return branch;
    }

    public RegionalBranch createRegionalBranch(String name, List<User> employees, List<Car> cars, List<RegionalBranch> children, RegionalBranch parent) {
        RegionalBranch branch = new RegionalBranch();
        branch.setName(name);
        branch.setParent(parent);
        branch.setCreationDate(currentTime);
        branch.setModificationDate(currentTime);
        if (employees != null) {
            for (User employee : employees) {
                branch.addEmployee(employee);
            }
        }
        if (cars != null) {
            for (Car car : cars) {
                branch.addCar(car);
            }
        }
        if (children != null) {
            for (RegionalBranch child : children) {
                branch.addChild(child);
            }
        }

        return branch;
    }

    public CarReservationRequest createReservationRequest(Car car, User user, LocalDateTime reservationStartDate, LocalDateTime reservationEndDate, CarReservationRequestState state) {
        CarReservationRequest reservationRequest = new CarReservationRequest();
        reservationRequest.setCar(car);
        reservationRequest.setUser(user);
        reservationRequest.setReservationStartDate(reservationStartDate);
        reservationRequest.setReservationEndDate(reservationEndDate);
        reservationRequest.setState(state);
        reservationRequest.setCreationDate(currentTime);
        reservationRequest.setModificationDate(currentTime);
        return reservationRequest;
    }
}
