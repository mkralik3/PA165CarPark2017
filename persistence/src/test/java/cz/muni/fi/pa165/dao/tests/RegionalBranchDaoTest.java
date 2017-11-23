/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao.tests;

import cz.muni.fi.pa165.dao.tests.support.TestObjectFactory;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import cz.muni.fi.pa165.enums.UserType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Tomas Pavuk
 */
public class RegionalBranchDaoTest extends TestBase {

    private final TestObjectFactory objectFactory = new TestObjectFactory();
    
    private LocalDateTime currentTime;

    @BeforeMethod
    public void setup() {
        currentTime = LocalDateTime.now();
    }

    @Test
    public void createReservation() {
        RegionalBranch parentBranch = objectFactory.createRegionalBranch("Parent");
        branchDao.save(parentBranch);
        assertThat(parentBranch.getId()).isGreaterThan(0);
    }

    @Test(dependsOnMethods = "createReservation")
    public void findAllRegionalBranches() {
        List<RegionalBranch> branchesToCreate = new ArrayList<>();
        branchesToCreate.add(objectFactory.createRegionalBranch("Branch1"));
        branchesToCreate.add(objectFactory.createRegionalBranch("Branch2"));

        for (RegionalBranch branch : branchesToCreate){
            branchDao.save(branch);
        }

        List<RegionalBranch> foundBranches = (List<RegionalBranch>) branchDao.findAll();
        
        for (RegionalBranch branch : branchesToCreate){
            assertThat(foundBranches).contains(branch);
        }
    }

    @Test(dependsOnMethods = "createReservation")
    public void findAllChildrenBranches() {
        RegionalBranch parentBranch = objectFactory.createRegionalBranch("Parent");
        branchDao.save(parentBranch);
        RegionalBranch childBranch = objectFactory.createRegionalBranch("Child", null, null, null, parentBranch);
        branchDao.save(childBranch);

        List<RegionalBranch> foundChildrenBranches = (List<RegionalBranch>) branchDao.findAllChildrenBranches(parentBranch.getName());

        assertThat(foundChildrenBranches.size()).isEqualTo(1);
        assertThat(foundChildrenBranches).contains(childBranch);
    }

    @Test(dependsOnMethods = "createReservation")
    public void findAllAvailableCarsForBranch() {
        Car car1 = objectFactory.createCar("Car1");
        Car car2 = objectFactory.createCar("Car2");
        Car car3 = objectFactory.createCar("Car3");
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.save(user);
        carDao.save(car1);
        carDao.save(car2);
        carDao.save(car3);
        List<Car> createdCars = Arrays.asList(car1, car2, car3);

        RegionalBranch branch = objectFactory.createRegionalBranch("Branch", null, createdCars, null, null);
        branchDao.save(branch);

        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car1, user, currentTime.minus(9, ChronoUnit.DAYS), currentTime.minus(7, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car2, user, currentTime.minus(9, ChronoUnit.DAYS), currentTime.plus(7, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        CarReservationRequest reservation3 = objectFactory.
                createReservationRequest(car3, user, currentTime.minus(9, ChronoUnit.DAYS), currentTime.plus(7, ChronoUnit.DAYS), CarReservationRequestState.DENIED);

        reservationDao.save(reservation1); //reservation1 is outdated car1 is already available
        reservationDao.save(reservation2); //reservation2 is still actual car2 isn't already available
        reservationDao.save(reservation3); //reservation3 is actual but was denied car3 is available

        Car car4 = objectFactory.createCar("Car4");
        Car car5 = objectFactory.createCar("Car5");
        carDao.save(car4);
        carDao.save(car5);
        List<Car> createdCars2 = Arrays.asList(car4, car5);
        RegionalBranch branch2 = objectFactory.createRegionalBranch("Branch2", null, createdCars2, null, null);
        branchDao.save(branch2);

        List<Car> foundCars = (List<Car>) branchDao.findAllAvailableCarsForBranch(branch.getName(),currentTime);
        assertThat(foundCars.size()).isEqualTo(2);
        assertThat(foundCars).contains(car1);
        assertThat(foundCars).contains(car3);
    }

    @Test(dependsOnMethods = "createReservation")
    public void findBranchById() {
        RegionalBranch branchToCreate = objectFactory.createRegionalBranch("Branch");
        branchDao.save(branchToCreate);

        RegionalBranch foundBranch = branchDao.findOne(branchToCreate.getId());

        assertThat(foundBranch).isNotNull();
        assertThat(foundBranch.getName()).isEqualTo(branchToCreate.getName());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullNameIsNotAllowed() {
        RegionalBranch branch = objectFactory.createRegionalBranch(null);
        branchDao.save(branch);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void branchCantBeItsOwnParent() {
        RegionalBranch branch = objectFactory.createRegionalBranch("Branch");
        branch.setParent(branch);
        branchDao.save(branch);
    }

    @Test(dependsOnMethods = {"createReservation","findBranchById"})
    public void updateBranch() {
        RegionalBranch branchToCreate = objectFactory.createRegionalBranch("Branch");
        RegionalBranch branchToUpdate = objectFactory.createRegionalBranch("UpdatedBranch");
        branchDao.save(branchToCreate);
        
        branchToUpdate.setId(branchToCreate.getId());
        branchDao.save(branchToUpdate);

        RegionalBranch foundBranch = branchDao.findOne(branchToCreate.getId());
        assertThat(foundBranch.getName()).isEqualTo(branchToUpdate.getName());
    }

    @Test(dependsOnMethods = {"createReservation","findBranchById"})
    public void deleteBranch() {
        RegionalBranch branch = objectFactory.createRegionalBranch("Branch");
        branchDao.save(branch);
        assertThat(branchDao.findOne(branch.getId())).isNotNull();
        branchDao.delete(branch);
        assertThat(branchDao.findOne(branch.getId())).isNull();
    }

    @Test()
    public void getAllManagersTest(){
        User user1 = objectFactory.createUser("User1", "1234567890", UserType.USER);
        User user2 = objectFactory.createUser("User2", "1234567890", UserType.BRANCH_MANAGER);
        User user3 = objectFactory.createUser("User3", "1234567890", UserType.BRANCH_MANAGER);
        User user4 = objectFactory.createUser("User4", "1234567890", UserType.USER);
        User user5 = objectFactory.createUser("User5", "1234567890", UserType.BRANCH_MANAGER);
        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);
        userDao.save(user4);
        userDao.save(user5);
        RegionalBranch branch = objectFactory.createRegionalBranch("Branch");
        branch.addEmployee(user1);
        branch.addEmployee(user2);
        branch.addEmployee(user3);
        branch.addEmployee(user4);
        branchDao.save(branch);
        RegionalBranch branch2 = objectFactory.createRegionalBranch("Branch2");
        branch2.addEmployee(user5);
        branchDao.save(branch2);

        List<User> managers = branchDao.getAllManagersInBranch(branch.getName());
        assertThat(managers).hasSize(2);
        assertThat(managers.get(0).getUserName()).isEqualTo("User2");
        assertThat(managers.get(1).getUserName()).isEqualTo("User3");
    }
}
