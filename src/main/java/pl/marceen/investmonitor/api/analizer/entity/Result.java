package pl.marceen.investmonitor.api.analizer.entity;

import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class Result {
    public List<Data> dataList;

    public List<Data> getDataList() {
        return dataList;
    }

    public Result setDataList(List<Data> dataList) {
        this.dataList = dataList;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "dataList=" + dataList +
                '}';
    }
}
