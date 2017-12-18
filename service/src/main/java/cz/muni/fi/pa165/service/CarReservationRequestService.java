package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.service.enums.*;
import cz.muni.fi.pa165.entity.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Martin Miskeje
 */
public interface CarReservationRequestService {

    /**
     * Create reservation request
     * @param request reservation which will be created
     * @return error codes if happen
     * @throws IllegalArgumentException if reservation is null
     */
    Set<CarReservationRequestOperationErrorCode> create(CarReservationRequest request);

    /**
     * Update reservation
     * @param request reservation which will be updated
     * @return error codes if happen
     * @throws IllegalArgumentException if reservation is null or it is not exist
     */
    Set<CarReservationRequestOperationErrorCode> update(CarReservationRequest request);

    /**
     * Delete particular reservation
     * @param reservationRequestId id of particular reservation
     * @return deleted reservation
     */
    CarReservationRequest delete(long reservationRequestId);

    /**
     * Get all reservations for particular regional branch in time period
     * @param regionalBranchIds particular regional branch
     * @param dateFrom start period
     * @param dateTo end period
     * @return list of all reservation for this time period and branch
     */
    List<CarReservationRequest> getAllForRegionalBranch(Set<Long> regionalBranchIds, LocalDateTime dateFrom, LocalDateTime dateTo);

    /**
     * Find all reservation
     * @return all reservation
     */
    List<CarReservationRequest> findAll();

    /**
     * Find particular reservation
     * @return particular reservation
     */
    CarReservationRequest findOne(long id);
}
