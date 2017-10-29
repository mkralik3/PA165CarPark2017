package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.CarReservationRequestState;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;

/**
 * The interface for car reservation entity
 * @author Matej Kralik
 */
public interface CarReservationRequestDAO {

    /**
     * Create reservation in database
     * @param reservation - particular reservation
     */
    void createReservationRequest(CarReservationRequest reservation);

    /**
     * Update particular reservation in database
     * @param reservation - particular reservation
     * @return updated reservation
     */
    CarReservationRequest updateReservationRequest(CarReservationRequest reservation);

    /**
     * Delete particular reservation
     * @param reservation - particular reservation
     */
    void deleteReservationRequest(CarReservationRequest reservation);

    /**
     * Find particular reservation by id
     * @param id - id of reservation
     * @return specific reservation or null if none exists
     */
    CarReservationRequest findReservationByID(Long id);

    /**
     * Find all reservations
     * @return collection of all reservations or null if none exists
     */
    Collection<CarReservationRequest> findAllReservations();

    /**
     * Find all reservations which start in the particular period
     * @param startDateFrom - start day of period
     * @param startDateTo - end day of period
     * @return collection of reservations which start in the specific period or null if none exists
    a     */
    Collection<CarReservationRequest> findAllReservationsWhichStartBetween(ZonedDateTime startDateFrom, ZonedDateTime startDateTo);

    /**
     * Find all reservations which end in the particular period
     * @param endDateFrom - start day of period
     * @param endDateTo - end day of period
     * @return collection of reservations which end in the specific period or null if none exists
a     */
    Collection<CarReservationRequest> findAllReservationsWhichEndBetween(ZonedDateTime endDateFrom, ZonedDateTime endDateTo);


    /**
     * Find all reservations which have same state
     * @param state - state of reservation
     * @return collection of reservations which have same state or null if none exists
     */
    Collection<CarReservationRequest> findAllReservationsByState(CarReservationRequestState state);

    /**
     * Find all reservations for particular car
     * @param car - particular car
     * @return collection of reservations which have same car or null if none exists
     */
    Collection<CarReservationRequest> findAllReservationsForCar(Car car);

    /**
     * Find all reservations for particular user
     * @param user - particular user
     * @return collection of reservations which have same user or null if none exists
     */
    Collection<CarReservationRequest> findAllReservationsForUser(User user);
}
