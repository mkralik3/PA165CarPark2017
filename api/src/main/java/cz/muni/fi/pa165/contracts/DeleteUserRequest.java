package cz.muni.fi.pa165.contracts;

/**
 *
 * @author Martin Miskeje
 */
public class DeleteUserRequest extends RequestBase {
    private long userId = 0;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
