package pl.marceen.investmonitor.api.pkotfi.control;

import pl.marceen.investmonitor.api.analizer.entity.Data;
import pl.marceen.investmonitor.api.analizer.entity.Result;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marcin Zaremba
 */
public class ResultMapper {
    public Result map(List<pl.marceen.investmonitor.api.pkotfi.entity.Data> pkoTfiDataList) {
        return new Result()
                .setDataList(pkoTfiDataList.stream().map(this::buildData).collect(Collectors.toList()));
    }

    private Data buildData(pl.marceen.investmonitor.api.pkotfi.entity.Data pkoTfiData) {
        return new Data()
                .setDate(pkoTfiData.getDate())
                .setValue(pkoTfiData.getValue());
    }
}
