package cz.muni.fi.pa165.contracts;

/**
 *
 * @author Martin Miskeje
 */
public abstract class ResponseBase {
    private Boolean isSuccess = false;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
