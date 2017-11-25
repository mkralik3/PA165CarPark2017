package cz.muni.fi.pa165.dto.results;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.enums.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Martin Miskeje
 */
public class UserOperationResult extends ResultWithData<UserDTO> {
    private final Set<UserOperationErrorCode> errorCodes = new HashSet<>();

    public Set<UserOperationErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
