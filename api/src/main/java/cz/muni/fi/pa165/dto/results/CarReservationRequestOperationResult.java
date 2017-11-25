package cz.muni.fi.pa165.dto.results;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.enums.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Martin Miskeje
 */
public class CarReservationRequestOperationResult extends  ResultWithData<CarReservationRequestDTO> {
    private final Set<CarReservationRequestOperationErrorCode> errorCodes = new HashSet<>();

    public Set<CarReservationRequestOperationErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
