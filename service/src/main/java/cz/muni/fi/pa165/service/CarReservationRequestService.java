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
    Set<CarReservationRequestOperationErrorCode> create(CarReservationRequest request);
    Set<CarReservationRequestOperationErrorCode> update(CarReservationRequest request);
    CarReservationRequest delete(long reservationRequestId);
    List<CarReservationRequest> getAllForRegionalBranch(Set<Long> regionalBranchIds, LocalDateTime dateFrom, LocalDateTime dateTo);
}
