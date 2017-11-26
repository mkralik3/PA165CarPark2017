package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface CarReservationRequestFacade {

    CarReservationRequestOperationResult createCarReservationRequest(CarReservationRequestDTO reservation);

    /*
        Approve/deny will be through this method
    */
    CarReservationRequestOperationResult updateCarReservationRequest(CarReservationRequestDTO reservation);

    SimpleResult deleteCarReservationRequest(long id);

    List<CarReservationRequestDTO> getAllForRegionalBranch(Set<Long> regionalBranchIds, LocalDateTime dateFrom, LocalDateTime dateTo);
}
