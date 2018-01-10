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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Tomas Pavuk
 */
public class CarReservationRequestDaoTest extends TestBase {

    private final TestObjectFactory objectFactory = new TestObjectFactory();

    private LocalDateTime currentTime;
    private Car car;
    private User user;

    @BeforeMethod
    public void setup() {
        car = objectFactory.createCar("Volvo");
        carDao.save(car);
        user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.save(user);

        currentTime = LocalDateTime.now();
    }

    @Test
    public void createReservation() {
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime.minus(9, ChronoUnit.DAYS), currentTime.plus(7, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        reservationDao.save(reservation1);
        assertThat(reservation1.getId()).isGreaterThan(0);
    }

    @Test(dependsOnMethods = "createReservation")
    public void findAllReservations() {
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime.minus(9, ChronoUnit.DAYS), currentTime.plus(7, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user, currentTime.plus(9, ChronoUnit.DAYS), currentTime.plus(10, ChronoUnit.DAYS), CarReservationRequestState.CREATED);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);

        List<CarReservationRequest> foundReservations = reservationDao.findAll();

        assertThat(foundReservations.size()).isEqualTo(2);
        assertThat(foundReservations).contains(reservation1);
        assertThat(foundReservations).contains(reservation2);
    }

    @Test(dependsOnMethods = "createReservation")
    public void findAllReservationsWhichStartBetween() {
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user, currentTime.plus(6, ChronoUnit.DAYS), currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);

        List<CarReservationRequest> reservations = reservationDao
                .findAllReservationsWhichStartBetween(
                        currentTime, currentTime.plus(4, ChronoUnit.DAYS));

        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations).contains(reservation1);
    }

    @Test(dependsOnMethods = "createReservation")
    public void findAllReservationsWhichEndBetween() {
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user, currentTime.plus(6, ChronoUnit.DAYS), currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);

        List<CarReservationRequest> reservations = reservationDao
                .findAllReservationsWhichEndBetween(
                        currentTime.plus(4, ChronoUnit.DAYS), currentTime.plus(9, ChronoUnit.DAYS));

        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations).contains(reservation2);
    }

    @Test(dependsOnMethods = "createReservation")
    public void findAllReservationsByState() {
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user, currentTime.plus(6, ChronoUnit.DAYS), currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.DENIED);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);

        List<CarReservationRequest> foundReservations = reservationDao.
                findAllReservationsByState(CarReservationRequestState.APPROVED);
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservation1);

        foundReservations = reservationDao.
                findAllReservationsByState(CarReservationRequestState.DENIED);
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservation2);
    }

    @Test(dependsOnMethods = "createReservation")
    public void findAllReservationsForCar() {
        Car car2 = objectFactory.createCar("Car2");
        carDao.save(car2);
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car2, user, currentTime, currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);

        List<CarReservationRequest> foundReservations = reservationDao.findAllReservationsByCar(car);
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservation1);
    }

    @Test(dependsOnMethods = "createReservation")
    public void findAllReservationsForUser() {
        User user2 = objectFactory.createUser("User2", "1234567890", UserType.USER);
        userDao.save(user2);
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user2, currentTime.plus(4, ChronoUnit.DAYS), currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);

        List<CarReservationRequest> foundReservations = reservationDao.
                findAllReservationsByUser(user);
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservation1);
    }

    @Test(dependsOnMethods = "createReservation")
    public void findReservationById() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        reservationDao.save(reservation);

        CarReservationRequest foundReservation = reservationDao.findOne(reservation.getId());

        assertThat(foundReservation).isNotNull();
        assertThat(foundReservation.getCar()).isEqualTo(reservation.getCar());
        assertThat(foundReservation.getUser()).isEqualTo(reservation.getUser());
        assertThat(foundReservation.getReservationStartDate()).isEqualTo(reservation.getReservationStartDate());
        assertThat(foundReservation.getReservationEndDate()).isEqualTo(reservation.getReservationEndDate());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullCarIsNotAllowed() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(null, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        reservationDao.save(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullUserIsNotAllowed() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, null, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        reservationDao.save(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullReservationStartDateIsNotAllowed() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, user, null, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        reservationDao.save(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullReservationEndDateIsNotAllowed() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, user, currentTime, null, CarReservationRequestState.APPROVED);
        reservationDao.save(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullReservationStateIsNotAllowed() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), null);
        reservationDao.save(reservation);
    }

    @Test(dependsOnMethods = {"createReservation", "findReservationById"})
    public void updateReservation() {
        CarReservationRequest reservationToCreate = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservationToUpdate = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        reservationDao.save(reservationToCreate);

        reservationToUpdate.setId(reservationToCreate.getId());
        reservationDao.save(reservationToUpdate);

        CarReservationRequest foundReservation = reservationDao.findOne(reservationToUpdate.getId());
        assertThat(foundReservation.getCar()).isEqualTo(reservationToUpdate.getCar());
        assertThat(foundReservation.getUser()).isEqualTo(reservationToUpdate.getUser());
        assertThat(foundReservation.getReservationStartDate()).isEqualTo(reservationToUpdate.getReservationStartDate());
        assertThat(foundReservation.getReservationEndDate()).isEqualTo(reservationToUpdate.getReservationEndDate());
    }

    @Test(dependsOnMethods = {"createReservation", "findReservationById"})
    public void deleteReservation() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.save(reservation);
        assertThat(reservationDao.findOne(reservation.getId())).isNotNull();
        reservationDao.delete(reservation);
        assertThat(reservationDao.findOne(reservation.getId())).isNull();
    }

    @Test
    public void getAllForMoreRegionalBranchTest() {
        Car car2 = objectFactory.createCar("Car2");
        carDao.save(car2);
        Car car3 = objectFactory.createCar("Car3");
        carDao.save(car3);

        RegionalBranch regionalBranch1 = objectFactory.createRegionalBranch("branch1");
        regionalBranch1.addCar(car);
        regionalBranch1.addCar(car2);
        branchDao.save(regionalBranch1);

        RegionalBranch regionalBranch2 = objectFactory.createRegionalBranch("branch2");
        regionalBranch2.addCar(car3);
        branchDao.save(regionalBranch2);

        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car2, user, currentTime, currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation3Other = objectFactory.
                createReservationRequest(car3, user, currentTime, currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);
        reservationDao.save(reservation3Other);


        List<CarReservationRequest> foundReservations = reservationDao.getAllForRegionalBranch(regionalBranch1.getId(), currentTime, currentTime.plus(4, ChronoUnit.DAYS));

        assertThat(foundReservations.contains(reservation1));
        assertThat(foundReservations.contains(reservation2));
    }

    @Test
    public void getAllForRegionalBranchAndChildrenTest() {
        LocalDateTime startPeriod = currentTime.minus(7, ChronoUnit.DAYS);
        LocalDateTime endPeriod = currentTime.plus(7, ChronoUnit.DAYS);

        Car car2 = objectFactory.createCar("Car2");
        carDao.save(car2);
        Car car3 = objectFactory.createCar("Car3");
        carDao.save(car3);
        Car car4 = objectFactory.createCar("Car4");
        carDao.save(car4);

        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user,startPeriod.plus(2, ChronoUnit.DAYS) , endPeriod.minus(1, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car2, user, startPeriod.plus(3, ChronoUnit.DAYS), endPeriod.minus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation3 = objectFactory.
                createReservationRequest(car3, user, startPeriod.plus(2, ChronoUnit.DAYS), endPeriod.minus(2, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation4Other = objectFactory.
                createReservationRequest(car4, user, startPeriod.plus(2, ChronoUnit.DAYS), endPeriod.minus(2, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);
        reservationDao.save(reservation3);
        reservationDao.save(reservation4Other);

        RegionalBranch branch1 = objectFactory.createRegionalBranch("branch1");
        branch1.addCar(car);
        branch1.addCar(car2);
        branchDao.save(branch1);
        RegionalBranch branch2 = objectFactory.createRegionalBranch("branch2");
        branch2.setParent(branch1);
        branch2.addCar(car3);
        branchDao.save(branch2);

        RegionalBranch branch3Other = objectFactory.createRegionalBranch("branch3");
        branch3Other.addCar(car4);
        branchDao.save(branch3Other);

        List<CarReservationRequest> foundReservations = reservationDao.getAllForRegionalBranchAndChildren(branch1.getId(),currentTime.minus(8, ChronoUnit.DAYS), currentTime);

        assertThat(foundReservations.size()).isEqualTo(3);
        assertThat(foundReservations).contains(reservation1,reservation2, reservation3);
    }


    @Test
    public void findAllOverlappedReservationsTest() {
        User user2 = objectFactory.createUser("User2", "1234567890", UserType.USER);
        userDao.save(user2);

        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime.minus(7, ChronoUnit.DAYS), currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user2, currentTime.minus(10, ChronoUnit.DAYS), currentTime.minus(1, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);

        List<CarReservationRequest> foundReservations = reservationDao.findAllOverlappedReservations(currentTime.minus(7, ChronoUnit.DAYS), currentTime.plus(3, ChronoUnit.DAYS), car.getId());

        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations.contains(reservation1));
    }
}
