package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;

import java.time.LocalDateTime;
import java.util.List;

public interface CarReservationRequestFacade {

    CarReservationRequestOperationResult createCarReservationRequest(CarReservationRequestDTO reservation);

    /*
        Approve/deny will be through this method
    */
    CarReservationRequestOperationResult updateCarReservationRequest(CarReservationRequestDTO reservation);

    SimpleResult deleteCarReservationRequest(long id);

    /*
    If includeChildren is set to true, requests of sub branches (recursively) will be also returned.
    */
    List<CarReservationRequestDTO> getAllForRegionalBranch(RegionalBranchDTO regionalBranch, Boolean includeChildren, LocalDateTime dateFrom, LocalDateTime dateTo);
}
