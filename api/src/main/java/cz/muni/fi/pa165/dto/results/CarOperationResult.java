package cz.muni.fi.pa165.dto.results;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.enums.CarOperationErrorCode;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Martin Miskeje
 */
public class CarOperationResult extends ResultWithData<CarDTO> {
    private final Set<CarOperationErrorCode> errorCodes = new HashSet<>();

    public Set<CarOperationErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
