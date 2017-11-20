package cz.muni.fi.pa165.contracts;

/**
 *
 * @author Martin Miskeje
 * @param <TData>
 */
public abstract class RequestBaseWithData<TData> extends RequestBase {
    private TData data = null;

    public TData getData() {
        return data;
    }

    public void setData(TData data) {
        this.data = data;
    }
}
