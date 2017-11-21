package cz.muni.fi.pa165.contracts;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Martin Miskeje
 */
public class UpdateUserResponse extends ResponseBaseWithData<User> {
    private final Set<UserErrorCode> errorCodes = new HashSet<UserErrorCode>();

    public Set<UserErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
