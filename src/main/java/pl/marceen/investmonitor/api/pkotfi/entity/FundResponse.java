package pl.marceen.investmonitor.api.pkotfi.entity;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class FundResponse {
    private Meta meta;

    @JsonbProperty("objects")
    private List<Data> dataList;

    public Meta getMeta() {
        return meta;
    }

    public FundResponse setMeta(Meta meta) {
        this.meta = meta;
        return this;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public FundResponse setDataList(List<Data> dataList) {
        this.dataList = dataList;
        return this;
    }

    @Override
    public String toString() {
        return "FundResponse{" +
                "meta=" + meta +
                ", dataList=" + dataList +
                '}';
    }
}
