package cz.muni.fi.pa165.dto.results;

/**
 *
 * @author Martin Miskeje
 */
public class ResultWithData<TData> extends SimpleResult {
    private TData data = null;

    public TData getData() {
        return data;
    }

    public void setData(TData data) {
        this.data = data;
    }
}
