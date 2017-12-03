package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.service.CarReservationRequestService;
import cz.muni.fi.pa165.service.CarService;
import cz.muni.fi.pa165.service.RegionalBranchService;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class InitializerImpl implements Initializer {

    private LocalDateTime currentTime = LocalDateTime.now();

    @Inject
    private CarService carService;

    @Inject
    private RegionalBranchService branchService;

    @Inject
    private UserService userService;

    @Inject
    private CarReservationRequestService reservationsService;

    @Override
    public void loadData() {
        createCars();
        createBranches();
        createUsers(); //TODO missing password ??
        /*TODO add other data*/
    }

    private Car commonCar = null;

    private void createCars(){
        commonCar = this.createCar("Audi A3");
        carService.createCar(commonCar);
        carService.createCar(this.createCar("Audi A8"));
        carService.createCar(this.createCar("BMW 3"));
        carService.createCar(this.createCar("Volvo XC90"));
        carService.createCar(this.createCar("Skoda Fabia"));
        carService.createCar(this.createCar("Ford Focus"));
    }

    private void createBranches(){
        List<Car> carsForFirst = new ArrayList<>();
        carsForFirst.add(commonCar);
        branchService.create(createRegionalBranch("Branch1",null,carsForFirst,null,null));
    }

    private void createUsers(){
        userService.create(createUser("Mato","testTest", UserType.ADMIN),"testTest");
        //TODO add
    }

    private Car createCar(String name) {
        Car result = new Car();
        result.setName(name);
        result.setCreationDate(currentTime);
        result.setModificationDate(currentTime);
        return result;
    }

    private User createUser(String username, String password, UserType type) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setCreationDate(currentTime);
        user.setModificationDate(currentTime);
        user.setType(type);
        return user;
    }

    private RegionalBranch createRegionalBranch(String name) {
        RegionalBranch branch = new RegionalBranch();
        branch.setName(name);
        branch.setCreationDate(currentTime);
        branch.setModificationDate(currentTime);
        return branch;
    }

    private RegionalBranch createRegionalBranch(String name, List<User> employees, List<Car> cars, List<RegionalBranch> children, RegionalBranch parent) {
        RegionalBranch branch = new RegionalBranch();
        branch.setName(name);
        branch.setParent(parent);
        branch.setCreationDate(currentTime);
        branch.setModificationDate(currentTime);
        if (employees != null) {
            for (User employee : employees) {
                branch.addEmployee(employee);
                userService.update(employee); // TODO due to: update branch in employee?
            }
        }
        if (cars != null) {
            for (Car car : cars) {
                branch.addCar(car);
                carService.updateCar(car); // TODO due to: update branch in car?
            }
        }
        if (children != null) {
            for (RegionalBranch child : children) {
                branch.addChild(child);
                branchService.update(child); // TODO due to: update branch in car?
            }
        }

        return branch;
    }

    private CarReservationRequest createReservationRequest(Car car, User user, LocalDateTime reservationStartDate, LocalDateTime reservationEndDate, CarReservationRequestState state) {
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
