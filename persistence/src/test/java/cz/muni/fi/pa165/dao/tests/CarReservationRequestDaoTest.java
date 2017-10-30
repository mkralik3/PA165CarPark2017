/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao.tests;

import cz.muni.fi.pa165.dao.tests.support.TestObjectFactory;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import cz.muni.fi.pa165.enums.UserType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.validation.ConstraintViolationException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.dao.InvalidDataAccessApiUsageException;

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
        carDao.createCar(car);
        user = objectFactory.createUser("User", "1234567890", UserType.USER);
        userDao.createUser(user);
        
        currentTime = LocalDateTime.now();
    }

    @Test
    public void findAllReservations() {
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime.minus(9, ChronoUnit.DAYS), currentTime.plus(7, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user, currentTime.plus(9, ChronoUnit.DAYS), currentTime.plus(10, ChronoUnit.DAYS), CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> foundReservations = (List<CarReservationRequest>) reservationDao.findAllReservations();

        assertThat(foundReservations.size()).isEqualTo(2);
        assertThat(foundReservations).contains(reservation1);
        assertThat(foundReservations).contains(reservation2);
    }

    @Test
    public void findAllReservationsWhichStartBetween() {
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user, currentTime.plus(6, ChronoUnit.DAYS), currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> reservations = (List<CarReservationRequest>) reservationDao
                .findAllReservationsWhichStartBetween(
                        currentTime, currentTime.plus(4, ChronoUnit.DAYS));

        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations).contains(reservation1);
    }

    @Test
    public void findAllReservationsWhichEndBetween() {
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user, currentTime.plus(6, ChronoUnit.DAYS), currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> reservations = (List<CarReservationRequest>) reservationDao
                .findAllReservationsWhichStartBetween(
                        currentTime.plus(4, ChronoUnit.DAYS), currentTime.plus(9, ChronoUnit.DAYS));

        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations).contains(reservation2);
    }

    @Test
    public void findAllReservationsByState() {
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user, currentTime.plus(6, ChronoUnit.DAYS), currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.DENIED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> foundReservations = (List<CarReservationRequest>) reservationDao.
                findAllReservationsByState(CarReservationRequestState.APPROVED);
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservation1);

        foundReservations = (List<CarReservationRequest>) reservationDao.
                findAllReservationsByState(CarReservationRequestState.DENIED);
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservation2);
    }

    @Test
    public void findAllReservationsForCar() {
        Car car2 = objectFactory.createCar("Car2");
        carDao.createCar(car2);
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car2, user, currentTime, currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> foundReservations = (List<CarReservationRequest>) reservationDao.
                findAllReservationsForCar(car);
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservation1);
    }

    @Test
    public void findAllReservationsForUser() {
        User user2 = objectFactory.createUser("User2", "1234567890", UserType.USER);
        userDao.createUser(user2);
        CarReservationRequest reservation1 = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservation2 = objectFactory.
                createReservationRequest(car, user2, currentTime.plus(4, ChronoUnit.DAYS), currentTime.plus(8, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> foundReservations = (List<CarReservationRequest>) reservationDao.
                findAllReservationsForUser(user);
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservation1);
    }

    @Test
    public void findReservationById() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        reservationDao.createReservationRequest(reservation);
        
        CarReservationRequest foundReservation = reservationDao.findReservationByID(reservation.getId());

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
        reservationDao.createReservationRequest(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullUserIsNotAllowed() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, null, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        reservationDao.createReservationRequest(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullReservationStartDateIsNotAllowed() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, user, null, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        reservationDao.createReservationRequest(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullReservationEndDateIsNotAllowed() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, user, currentTime, null, CarReservationRequestState.APPROVED);
        reservationDao.createReservationRequest(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullReservationStateIsNotAllowed() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), null);
        reservationDao.createReservationRequest(reservation);
    }

    @Test
    public void updateReservation() {
        CarReservationRequest reservationToCreate = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        CarReservationRequest reservationToUpdate = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);
        reservationDao.createReservationRequest(reservationToCreate);
        
        reservationToUpdate.setId(reservationToCreate.getId());
        reservationDao.updateReservationRequest(reservationToUpdate);
        
        CarReservationRequest foundReservation = reservationDao.findReservationByID(reservationToUpdate.getId());
        assertThat(foundReservation.getCar()).isEqualTo(reservationToUpdate.getCar());
        assertThat(foundReservation.getUser()).isEqualTo(reservationToUpdate.getUser());
        assertThat(foundReservation.getReservationStartDate()).isEqualTo(reservationToUpdate.getReservationStartDate());
        assertThat(foundReservation.getReservationEndDate()).isEqualTo(reservationToUpdate.getReservationEndDate());
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteNullReservationIsNotAllowed() {
        reservationDao.deleteReservationRequest(null);
    }

    @Test
    public void deleteReservation() {
        CarReservationRequest reservation = objectFactory.
                createReservationRequest(car, user, currentTime, currentTime.plus(3, ChronoUnit.DAYS), CarReservationRequestState.APPROVED);

        reservationDao.createReservationRequest(reservation);
        assertThat(reservationDao.findReservationByID(reservation.getId())).isNotNull();
        reservationDao.deleteReservationRequest(reservation);
        assertThat(reservationDao.findReservationByID(reservation.getId())).isNull();
    }
}
