package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * The interface for car reservation entity
 * @author Matej Kralik
 */
public interface CarReservationRequestDAO extends CrudRepository<CarReservationRequest, Long> {

    /**
     * Find all reservations
     * @return reservations
     */
    List<CarReservationRequest> findAll();

    /**
     * Find all reservations which start in the particular period
     * @param startDateFrom - start day of period
     * @param startDateTo - end day of period
     * @return collection of reservations which start in the specific period or null if none exists
     */
    @Query("SELECT r FROM CarReservationRequest r WHERE r.reservationStartDate BETWEEN :startDataFrom AND :startDateTo")
    List<CarReservationRequest> findAllReservationsWhichStartBetween(@Param("startDataFrom") LocalDateTime startDateFrom,
                                                                     @Param("startDateTo") LocalDateTime startDateTo);

    /**
     * Find all reservations which end in the particular period
     * @param endDateFrom - start day of period
     * @param endDateTo - end day of period
     * @return collection of reservations which end in the specific period or null if none exists
     */
    @Query("SELECT r FROM CarReservationRequest r WHERE r.reservationEndDate BETWEEN :endDataFrom AND :endDateTo")
    List<CarReservationRequest> findAllReservationsWhichEndBetween(@Param("endDataFrom") LocalDateTime endDateFrom,
                                                                   @Param("endDateTo") LocalDateTime endDateTo);


    /**
     * Find all reservations which have same state
     * @param state - state of reservation
     * @return collection of reservations which have same state or null if none exists
     */
    List<CarReservationRequest> findAllReservationsByState(CarReservationRequestState state);

    /**
     * Find all reservations for particular car
     * @param car - particular car
     * @return collection of reservations which have same car or null if none exists
     */
    List<CarReservationRequest> findAllReservationsByCar(Car car);

    /**
     * Find all reservations for particular user
     * @param user - particular user
     * @return collection of reservations which have same user or null if none exists
     */
    List<CarReservationRequest> findAllReservationsByUser(User user);
    
    @Query("SELECT r FROM CarReservationRequest r WHERE (r.car.id = :carId) AND (r.reservationStartDate >= :startDate) AND (r.reservationEndDate <= :endDate)")
    List<CarReservationRequest> findAllOverlappedReservations(@Param("startDate") LocalDateTime startDate,
                                                              @Param("endDate") LocalDateTime endDate,
                                                              @Param("carId") long carId);
    
    @Query("SELECT r FROM CarReservationRequest r INNER JOIN r.car c INNER JOIN c.regionalBranch b WHERE b.id IN :regionalBranchIds AND (r.reservationStartDate <= :dateTo) AND (:dateFrom <= r.reservationEndDate)")
    List<CarReservationRequest> getAllForRegionalBranch(@Param("regionalBranchIds")Set<Long> regionalBranchIds,
                                                        @Param("dateFrom") LocalDateTime dateFrom,
                                                        @Param("dateTo") LocalDateTime dateTo);
}
