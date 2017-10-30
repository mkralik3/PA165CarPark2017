/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao.tests;

import cz.muni.fi.pa165.DateTimeProvider;
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
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.dao.InvalidDataAccessApiUsageException;

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
    public void findAllRegionalBranches() {
        List<RegionalBranch> branchesToCreate = new ArrayList<>();
        branchesToCreate.add(objectFactory.createRegionalBranch("Branch1"));
        branchesToCreate.add(objectFactory.createRegionalBranch("Branch2"));

        for (RegionalBranch branch : branchesToCreate){
            branchDao.createRegionalBranch(branch);
        }

        List<RegionalBranch> foundBranches = (List<RegionalBranch>) branchDao.findAllRegionalBranches();
        
        for (RegionalBranch branch : branchesToCreate){
            assertThat(foundBranches).contains(branch);
        }
    }

    @Test
    public void findAllChildrenBranches() {
        RegionalBranch parentBranch = objectFactory.createRegionalBranch("Parent");
        branchDao.createRegionalBranch(parentBranch);
        RegionalBranch childBranch = objectFactory.createRegionalBranch("Child", null, null, null, parentBranch);
        branchDao.createRegionalBranch(childBranch);

        List<RegionalBranch> foundChildrenBranches = (List<RegionalBranch>) branchDao.findAllChildrenBranches(parentBranch);

        assertThat(foundChildrenBranches.size()).isEqualTo(1);
        assertThat(foundChildrenBranches).contains(childBranch);
    }

    @Test
    public void findAllAvailableCarsForBranch() {
        Car car1 = objectFactory.createCar("Car1");
        Car car2 = objectFactory.createCar("Car2");
        Car car3 = objectFactory.createCar("Car3");
        User user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.createUser(user);
        carDao.createCar(car1);
        carDao.createCar(car2);
        carDao.createCar(car3);
        List<Car> createdCars = Arrays.asList(car1, car2, car3);

        RegionalBranch branch = objectFactory.createRegionalBranch("Branch", null, createdCars, null, null);
        branchDao.createRegionalBranch(branch);

        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car1, user, currentTime.minus(9, ChronoUnit.DAYS), currentTime.minus(7, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car2, user, currentTime.minus(9, ChronoUnit.DAYS), currentTime.plus(7, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        CarReservationRequest reservation3 = objectFactory.
                createReservationRequest(car3, user, currentTime.minus(9, ChronoUnit.DAYS), currentTime.plus(7, ChronoUnit.DAYS), CarReservationRequestState.DENIED);

        reservationDao.createReservationRequest(reservation1); //reservation1 is outdated car1 is already available
        reservationDao.createReservationRequest(reservation2); //reservation2 is still actual car2 isn't already available
        reservationDao.createReservationRequest(reservation3); //reservation3 is actual but was denied car3 is available

        List<Car> foundCars = (List<Car>) branchDao.findAllAvailableCarsForBranch(branch);
        assertThat(foundCars.size()).isEqualTo(2);
        assertThat(foundCars).contains(car1);
        assertThat(foundCars).contains(car3);
    }

    @Test
    public void findBranchById() {
        RegionalBranch branchToCreate = objectFactory.createRegionalBranch("Branch");
        branchDao.createRegionalBranch(branchToCreate);

        RegionalBranch foundBranch = branchDao.findRegionalBranchById(branchToCreate.getId());

        assertThat(foundBranch).isNotNull();
        assertThat(foundBranch.getName()).isEqualTo(branchToCreate.getName());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullNameIsNotAllowed() {
        RegionalBranch branch = objectFactory.createRegionalBranch(null);
        branchDao.createRegionalBranch(branch);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void branchCantBeItsOwnParent() {
        RegionalBranch branch = objectFactory.createRegionalBranch("Branch");
        branch.setParent(branch);
        branchDao.createRegionalBranch(branch);
    }

    @Test
    public void updateBranch() {
        RegionalBranch branchToCreate = objectFactory.createRegionalBranch("Branch");
        RegionalBranch branchToUpdate = objectFactory.createRegionalBranch("UpdatedBranch");
        branchDao.createRegionalBranch(branchToCreate);
        
        branchToUpdate.setId(branchToCreate.getId());
        branchDao.updateRegionalBranch(branchToUpdate);

        RegionalBranch foundBranch = branchDao.findRegionalBranchById(branchToCreate.getId());
        assertThat(foundBranch.getName()).isEqualTo(branchToUpdate.getName());
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteNullBranchIsNotAllowed() {
        branchDao.deleteRegionalBranch(null);
    }

    @Test
    public void deleteBranch() {
        RegionalBranch branch = objectFactory.createRegionalBranch("Branch");
        branchDao.createRegionalBranch(branch);
        assertThat(branchDao.findRegionalBranchById(branch.getId())).isNotNull();
        branchDao.deleteRegionalBranch(branch);
        assertThat(branchDao.findRegionalBranchById(branch.getId())).isNull();
    }
}
