package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface CarReservationRequestFacade {

    /**
     * Create reservation request
     * @param reservation reservation which will be created
     * @return result of operation with data
     * @throws IllegalArgumentException if reservation is null
     */
    CarReservationRequestOperationResult createCarReservationRequest(CarReservationRequestDTO reservation);

    /**
     * Update reservation
     * @param reservation reservation which will be updated
     * @return result of operation with data
     * @throws IllegalArgumentException if reservation is null or it is not exist
     */
    CarReservationRequestOperationResult updateCarReservationRequest(CarReservationRequestDTO reservation);

    /**
     * Delete particular car reservation
     * @param id id of particular reservation
     * @return simple result (isSuccess = true if found and deleted, otherwise false)
     */
    SimpleResult deleteCarReservationRequest(long id);

    /**
     * Get all reservations for particular regional branch in time period
     * @param regionalBranchId particular regional branch
     * @param dateFrom start period
     * @param dateTo end period
     * @return list of all reservation for this time period and branch
     */
    List<CarReservationRequestDTO> getAllForRegionalBranch(long regionalBranchId, LocalDateTime dateFrom, LocalDateTime dateTo);

    /**
     * Get all reservations for particular regional branch and its children in time period
     * @param regionalBranchId particular regional branch
     * @param dateFrom start period
     * @param dateTo end period
     * @return list of all reservation for this time period and branch
     */
    List<CarReservationRequestDTO> getAllForRegionalBranchAndChildren(long regionalBranchId, LocalDateTime dateFrom, LocalDateTime dateTo);

    /**
     * Find all reservations
     * @return all reservations
     */
    List<CarReservationRequestDTO> findAll();

    /**
     * Find particular reservation
     * @param id - id of particular reservatuib
     * @return particular reservation or null
     */
    CarReservationRequestDTO findOne(long id);
}
