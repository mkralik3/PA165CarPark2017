/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import cz.muni.fi.pa165.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Tomas Pavuk
 */

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class RegionalBranchDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RegionalBranchDAO branchDao;

    @Autowired
    private CarDAO carDao;

    @Autowired
    private CarReservationRequestDAO reservationDao;

    @Autowired
    private UserDAO userDao;

    private User user;
    private Clock testClock;
    private LocalDateTime currentTime;

    @BeforeMethod
    public void createUserAndClock() {
        user = new User();
        user.setUserName("User1");
        user.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        user.setPassword("1234567890");
        user.setType(UserType.USER);
        userDao.createUser(user);

        testClock = mock(Clock.class);
        when(testClock.instant()).thenAnswer((invocation) -> currentTime);
        currentTime = LocalDateTime.now();
    }

    @Test
    public void findAllRegionalBranches() {
        RegionalBranch branch1 = new RegionalBranch();
        RegionalBranch branch2 = new RegionalBranch();
        branch1.setName("Branch1");
        branch1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));

        branch2.setName("Branch2");
        branch2.setCreationDate(LocalDateTime.of(2012, Month.MARCH, 20, 10, 10));

        branchDao.createRegionalBranch(branch1);
        branchDao.createRegionalBranch(branch2);

        List<RegionalBranch> branches = (List<RegionalBranch>) branchDao.findAllRegionalBranches();

        assertThat(branches.size()).isEqualTo(2);
        assertThat(branches).contains(branch1);
        assertThat(branches).contains(branch2);
    }

    @Test
    public void findAllChildrenBranches() {
        RegionalBranch parentBranch = new RegionalBranch();
        RegionalBranch branch1 = new RegionalBranch();
        RegionalBranch branch2 = new RegionalBranch();
        parentBranch.setName("ParentBranch");
        parentBranch.setCreationDate(LocalDateTime.of(2015, Month.MARCH, 20, 10, 10));
        branchDao.createRegionalBranch(parentBranch);

        branch1.setName("Branch1");
        branch1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        branch1.setParent(parentBranch);

        branch2.setName("Branch2");
        branch2.setCreationDate(LocalDateTime.of(2012, Month.MARCH, 20, 10, 10));
        branch2.setParent(parentBranch);

        branchDao.createRegionalBranch(branch1);
        branchDao.createRegionalBranch(branch2);

        List<RegionalBranch> branches = (List<RegionalBranch>) branchDao.findAllChildrenBranches(parentBranch);

        assertThat(branches.size()).isEqualTo(2);
        assertThat(branches).contains(branch1);
        assertThat(branches).contains(branch2);
    }

    @Test
    public void findAllAvailableCarsForBranch() {
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        car1.setName("Car1");
        car1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        car2.setName("Car2");
        car2.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        car3.setName("Car3");
        car3.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        carDao.createCar(car1);
        carDao.createCar(car2);
        carDao.createCar(car3);

        RegionalBranch branch = new RegionalBranch();
        branch.setName("Branch");
        branch.setCreationDate(LocalDateTime.of(2015, Month.MARCH, 20, 10, 10));
        branch.addCar(car1);
        branch.addCar(car2);
        branch.addCar(car3);
        branchDao.createRegionalBranch(branch);

        CarReservationRequest reservation1 = new CarReservationRequest(testClock);
        reservation1.setUser(user);
        reservation1.setCar(car1);
        reservation1.setReservationStartDate(currentTime.minus(9, ChronoUnit.DAYS));
        reservation1.setReservationEndDate(currentTime.minus(7, ChronoUnit.DAYS));
        reservation1.setState(CarReservationRequestState.APPROVED);

        CarReservationRequest reservation2 = new CarReservationRequest(testClock);
        reservation2.setUser(user);
        reservation2.setCar(car2);
        reservation2.setReservationStartDate(currentTime.minus(9, ChronoUnit.DAYS));
        reservation2.setReservationEndDate(currentTime.plus(7, ChronoUnit.DAYS));
        reservation2.setState(CarReservationRequestState.APPROVED);

        CarReservationRequest reservation3 = new CarReservationRequest(testClock);
        reservation3.setUser(user);
        reservation3.setCar(car3);
        reservation3.setReservationStartDate(currentTime.minus(9, ChronoUnit.DAYS));
        reservation3.setReservationEndDate(currentTime.plus(7, ChronoUnit.DAYS));
        reservation3.setState(CarReservationRequestState.DENIED);

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
        RegionalBranch branch = new RegionalBranch();
        branch.setName("Branch");
        branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        branchDao.createRegionalBranch(branch);

        RegionalBranch foundBranch = branchDao.findRegionalBranchById(branch.getId());

        assertThat(foundBranch).isNotNull();
        assertThat(foundBranch.getName()).isEqualTo("Branch");
    }

    @Test()
    public void createBranchSavesBranchProperties() {
        RegionalBranch branch = new RegionalBranch();
        branch.setName("Branch");
        branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        branch.setModificationDate(LocalDateTime.of(2017, Month.MAY, 20, 10, 10));
        branchDao.createRegionalBranch(branch);

        RegionalBranch foundBranch = branchDao.findRegionalBranchById(branch.getId());

        assertThat(foundBranch.getName()).isEqualTo("Branch");
        assertThat(foundBranch.getCreationDate()).isEqualTo(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        assertThat(foundBranch.getModificationDate()).isEqualTo(LocalDateTime.of(2017, Month.MAY, 20, 10, 10));
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullNameIsNotAllowed() {
        RegionalBranch branch = new RegionalBranch();
        branch.setName(null);
        branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        branchDao.createRegionalBranch(branch);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullBranchCreationDateIsNotAllowed() {
        RegionalBranch branch = new RegionalBranch();
        branch.setName("Branch");
        branch.setCreationDate(null);
        branchDao.createRegionalBranch(branch);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void branchCantBeItsOwnParent() {
        RegionalBranch branch = new RegionalBranch();
        branch.setName("User");
        branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        branch.setParent(branch);
        branchDao.createRegionalBranch(branch);
    }

    @Test()
    public void updateUser() {
        RegionalBranch branch = new RegionalBranch();
        branch.setName("Branch");
        branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        branchDao.createRegionalBranch(branch);

        branch.setName("BranchWithChangedName");
        branch.setCreationDate(LocalDateTime.of(2016, Month.FEBRUARY, 20, 10, 10));

        RegionalBranch foundBranch = branchDao.findRegionalBranchById(branch.getId());

        assertThat(foundBranch.getName()).isEqualTo("BranchWithChangedName");
        assertThat(foundBranch.getCreationDate()).isEqualTo(LocalDateTime.of(2016, Month.FEBRUARY, 20, 10, 10));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNullBranchIsNotAllowed() {
        branchDao.updateRegionalBranch(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void deleteNullBranchIsNotAllowed() {
        branchDao.deleteRegionalBranch(null);
    }

    @Test()
    public void deleteBranch() {
        RegionalBranch branch = new RegionalBranch();
        branch.setName("Branch");
        branch.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        branchDao.createRegionalBranch(branch);
        assertThat(branchDao.findRegionalBranchById(branch.getId())).isNotNull();
        branchDao.deleteRegionalBranch(branch);
        assertThat(branchDao.findRegionalBranchById(branch.getId())).isNull();
    }


}
