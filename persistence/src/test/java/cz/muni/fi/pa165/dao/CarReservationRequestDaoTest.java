/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
public class CarReservationRequestDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private UserDAO userDao;

    @Autowired
    private CarDAO carDao;

    @Autowired
    private CarReservationRequestDAO reservationDao;

    @PersistenceContext
    private EntityManager em;

    private Clock testClock;
    private Car car1;
    private Car car2;
    private User user;
    private LocalDateTime currentTime;

    @BeforeMethod
    public void createCarsAndUsers() {
        car1 = new Car();
        car2 = new Car();
        car1.setName("Car1");
        car1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        car2.setName("Car2");
        car2.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        car2.setActivationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 20));

        carDao.createCar(car1);
        carDao.createCar(car2);

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
    public void findAllReservations() {
        CarReservationRequest reservation1 = new CarReservationRequest(testClock);
        CarReservationRequest reservation2 = new CarReservationRequest(testClock);
        reservation1.setCar(car1);
        reservation1.setUser(user);
        reservation1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation1.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation1.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation1.setState(CarReservationRequestState.CREATED);

        reservation2.setCar(car1);
        reservation2.setUser(user);
        reservation2.setCreationDate(LocalDateTime.of(2017, Month.APRIL, 20, 10, 10));
        reservation2.setReservationStartDate(currentTime.plus(7, ChronoUnit.DAYS));
        reservation2.setReservationEndDate(currentTime.plus(10, ChronoUnit.DAYS));
        reservation2.setState(CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> reservations = (List<CarReservationRequest>) reservationDao.findAllReservations();

        assertThat(reservations.size()).isEqualTo(2);
        assertThat(reservations).contains(reservation1);
        assertThat(reservations).contains(reservation2);
    }

    @Test
    public void findAllReservationsWhichStartBetween() {
        CarReservationRequest reservation1 = new CarReservationRequest(testClock);
        CarReservationRequest reservation2 = new CarReservationRequest(testClock);
        reservation1.setCar(car1);
        reservation1.setUser(user);
        reservation1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation1.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation1.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation1.setState(CarReservationRequestState.CREATED);

        reservation2.setCar(car1);
        reservation2.setUser(user);
        reservation2.setCreationDate(LocalDateTime.of(2017, Month.APRIL, 20, 10, 10));
        reservation2.setReservationStartDate(currentTime.plus(7, ChronoUnit.DAYS));
        reservation2.setReservationEndDate(currentTime.plus(10, ChronoUnit.DAYS));
        reservation2.setState(CarReservationRequestState.CREATED);

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
        CarReservationRequest reservation1 = new CarReservationRequest(testClock);
        CarReservationRequest reservation2 = new CarReservationRequest(testClock);
        reservation1.setCar(car1);
        reservation1.setUser(user);
        reservation1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation1.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation1.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation1.setState(CarReservationRequestState.CREATED);

        reservation2.setCar(car1);
        reservation2.setUser(user);
        reservation2.setCreationDate(LocalDateTime.of(2017, Month.APRIL, 20, 10, 10));
        reservation2.setReservationStartDate(currentTime.plus(7, ChronoUnit.DAYS));
        reservation2.setReservationEndDate(currentTime.plus(10, ChronoUnit.DAYS));
        reservation2.setState(CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> reservations = (List<CarReservationRequest>) reservationDao
                .findAllReservationsWhichEndBetween(
                        currentTime.plus(8, ChronoUnit.DAYS),
                        currentTime.plus(12, ChronoUnit.DAYS));

        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations).contains(reservation2);
    }

    @Test
    public void findAllReservationsByState() {
        CarReservationRequest reservation1 = new CarReservationRequest(testClock);
        CarReservationRequest reservation2 = new CarReservationRequest(testClock);
        reservation1.setCar(car1);
        reservation1.setUser(user);
        reservation1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation1.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation1.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation1.setState(CarReservationRequestState.CREATED);

        reservation2.setCar(car1);
        reservation2.setUser(user);
        reservation2.setCreationDate(LocalDateTime.of(2017, Month.APRIL, 20, 10, 10));
        reservation2.setReservationStartDate(currentTime.plus(7, ChronoUnit.DAYS));
        reservation2.setReservationEndDate(currentTime.plus(10, ChronoUnit.DAYS));
        reservation2.setState(CarReservationRequestState.APPROVED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> foundReservations = (List<CarReservationRequest>) reservationDao.
                findAllReservationsByState(CarReservationRequestState.CREATED);
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservation1);

        foundReservations = (List<CarReservationRequest>) reservationDao.
                findAllReservationsByState(CarReservationRequestState.APPROVED);
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservation2);
    }

    @Test
    public void findAllReservationsForCar() {
        CarReservationRequest reservation1 = new CarReservationRequest(testClock);
        CarReservationRequest reservation2 = new CarReservationRequest(testClock);
        reservation1.setCar(car1);
        reservation1.setUser(user);
        reservation1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation1.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation1.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation1.setState(CarReservationRequestState.CREATED);

        reservation2.setCar(car1);
        reservation2.setUser(user);
        reservation2.setCreationDate(LocalDateTime.of(2017, Month.APRIL, 20, 10, 10));
        reservation2.setReservationStartDate(currentTime.plus(7, ChronoUnit.DAYS));
        reservation2.setReservationEndDate(currentTime.plus(10, ChronoUnit.DAYS));
        reservation2.setState(CarReservationRequestState.APPROVED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> foundReservations = (List<CarReservationRequest>) reservationDao.
                findAllReservationsForCar(car1);
        assertThat(foundReservations.size()).isEqualTo(2);
        assertThat(foundReservations).contains(reservation1);
        assertThat(foundReservations).contains(reservation2);
    }

    @Test
    public void findAllReservationsForUser() {
        CarReservationRequest reservation1 = new CarReservationRequest(testClock);
        CarReservationRequest reservation2 = new CarReservationRequest(testClock);
        reservation1.setCar(car1);
        reservation1.setUser(user);
        reservation1.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation1.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation1.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation1.setState(CarReservationRequestState.CREATED);

        reservation2.setCar(car1);
        reservation2.setUser(user);
        reservation2.setCreationDate(LocalDateTime.of(2017, Month.APRIL, 20, 10, 10));
        reservation2.setReservationStartDate(currentTime.plus(7, ChronoUnit.DAYS));
        reservation2.setReservationEndDate(currentTime.plus(10, ChronoUnit.DAYS));
        reservation2.setState(CarReservationRequestState.APPROVED);

        reservationDao.createReservationRequest(reservation1);
        reservationDao.createReservationRequest(reservation2);

        List<CarReservationRequest> foundReservations = (List<CarReservationRequest>) reservationDao.
                findAllReservationsForUser(user);
        assertThat(foundReservations.size()).isEqualTo(2);
        assertThat(foundReservations).contains(reservation1);
        assertThat(foundReservations).contains(reservation2);
    }

    @Test
    public void findReservationById() {
        CarReservationRequest reservation = new CarReservationRequest(testClock);
        reservation.setCar(car1);
        reservation.setUser(user);
        reservation.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation.setState(CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation);
        CarReservationRequest foundReservation = reservationDao.findReservationByID(reservation.getId());

        assertThat(foundReservation).isNotNull();
        assertThat(foundReservation.getCreationDate()).isEqualTo(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        assertThat(foundReservation.getCar()).isEqualTo(car1);
        assertThat(foundReservation.getUser()).isEqualTo(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullCarIsNotAllowed() {
        CarReservationRequest reservation = new CarReservationRequest(testClock);
        reservation.setCar(null);
        reservation.setUser(user);
        reservation.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation.setState(CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullUserIsNotAllowed() {
        CarReservationRequest reservation = new CarReservationRequest(testClock);
        reservation.setCar(car1);
        reservation.setUser(null);
        reservation.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation.setState(CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullReservationCreationDateIsNotAllowed() {
        CarReservationRequest reservation = new CarReservationRequest(testClock);
        reservation.setCar(car1);
        reservation.setUser(user);
        reservation.setCreationDate(null);
        reservation.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation.setState(CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullReservationStartDateIsNotAllowed() {
        CarReservationRequest reservation = new CarReservationRequest(testClock);
        reservation.setCar(car1);
        reservation.setUser(user);
        reservation.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation.setReservationStartDate(null);
        reservation.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation.setState(CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullReservationEndDateIsNotAllowed() {
        CarReservationRequest reservation = new CarReservationRequest(testClock);
        reservation.setCar(car1);
        reservation.setUser(user);
        reservation.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation.setReservationStartDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation.setReservationEndDate(null);
        reservation.setState(CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullReservationStateIsNotAllowed() {
        CarReservationRequest reservation = new CarReservationRequest(testClock);
        reservation.setCar(car1);
        reservation.setUser(user);
        reservation.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation.setState(null);

        reservationDao.createReservationRequest(reservation);
    }

    @Test()
    public void updateReservation() {
        CarReservationRequest reservation = new CarReservationRequest(testClock);
        reservation.setCar(car1);
        reservation.setUser(user);
        reservation.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation.setState(CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation);

        reservation.setState(CarReservationRequestState.DENIED);

        CarReservationRequest foundReservation = reservationDao.findReservationByID(reservation.getId());
        assertThat(foundReservation.getState()).isEqualTo(CarReservationRequestState.DENIED);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNullBranchIsNotAllowed() {
        reservationDao.updateReservationRequest(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void deleteNullBranchIsNotAllowed() {
        reservationDao.deleteReservationRequest(null);
    }

    @Test()
    public void deleteReservation() {
        CarReservationRequest reservation = new CarReservationRequest(testClock);
        reservation.setCar(car1);
        reservation.setUser(user);
        reservation.setCreationDate(LocalDateTime.of(2017, Month.MARCH, 20, 10, 10));
        reservation.setReservationStartDate(currentTime.plus(1, ChronoUnit.DAYS));
        reservation.setReservationEndDate(currentTime.plus(5, ChronoUnit.DAYS));
        reservation.setState(CarReservationRequestState.CREATED);

        reservationDao.createReservationRequest(reservation);
        assertThat(reservationDao.findReservationByID(reservation.getId())).isNotNull();
        reservationDao.deleteReservationRequest(reservation);
        assertThat(reservationDao.findReservationByID(reservation.getId())).isNull();
    }
}
