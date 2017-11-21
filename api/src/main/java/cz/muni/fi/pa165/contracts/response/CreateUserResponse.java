package cz.muni.fi.pa165.contracts.response;

import cz.muni.fi.pa165.contracts.ResponseBaseWithData;
import cz.muni.fi.pa165.contracts.UserDTO;
import cz.muni.fi.pa165.enums.UserErrorCode;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Martin Miskeje
 */
public class CreateUserResponse extends ResponseBaseWithData<UserDTO> {
    private final Set<UserErrorCode> errorCodes = new HashSet<UserErrorCode>();

    public Set<UserErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
