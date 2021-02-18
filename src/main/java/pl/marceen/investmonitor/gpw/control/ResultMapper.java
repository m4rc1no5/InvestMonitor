package pl.marceen.investmonitor.gpw.control;

import pl.marceen.investmonitor.analizer.entity.Data;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.converter.boundary.DateConverter;
import pl.marceen.investmonitor.gpw.entity.Response;
import pl.marceen.investmonitor.gpw.entity.ResponseData;

import java.util.stream.Collectors;

/**
 * @author Marcin Zaremba
 */
public class ResultMapper {
    public Result map(Response response) {
        return new Result()
                .setDataList(
                        response.getData().stream()
                                .map(this::buildData)
                                .collect(Collectors.toList())
                );
    }

    private Data buildData(ResponseData responseData) {
        return new Data()
                .setDate(DateConverter.convertFromTimestamp(responseData.getT()))
                .setValue(responseData.getC());
    }
}
