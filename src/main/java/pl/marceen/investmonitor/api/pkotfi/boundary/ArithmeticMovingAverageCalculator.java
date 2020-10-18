package pl.marceen.investmonitor.api.pkotfi.boundary;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.api.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.api.analizer.entity.Data;
import pl.marceen.investmonitor.api.analizer.entity.Result;
import pl.marceen.investmonitor.api.network.control.HttpClientProducer;
import pl.marceen.investmonitor.api.network.control.HttpExecutor;
import pl.marceen.investmonitor.api.pkotfi.control.RequestBuilder;
import pl.marceen.investmonitor.api.pkotfi.control.ResultGetter;
import pl.marceen.investmonitor.api.pkotfi.control.ResultMapper;
import pl.marceen.investmonitor.api.pkotfi.control.UrlBuilder;
import pl.marceen.investmonitor.api.pkotfi.entity.Subfund;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class ArithmeticMovingAverageCalculator {
    private static final Logger logger = LoggerFactory.getLogger(ArithmeticMovingAverageCalculator.class);

    private final ResultGetter resultGetter;
    private final ArithmeticMovingAverage arithmeticMovingAverage;
    private final OkHttpClient httpClient;

    public ArithmeticMovingAverageCalculator() {
        resultGetter = new ResultGetter(new UrlBuilder(), new RequestBuilder(), new HttpExecutor<>(), new ResultMapper());
        arithmeticMovingAverage = new ArithmeticMovingAverage();
        httpClient = new HttpClientProducer().produce();
    }

    public void calculate(int numberOfMonths) {
        Arrays.stream(Subfund.values())
                .filter(Subfund::isActive)
                .forEach(subfund -> calculate(httpClient, subfund, numberOfMonths));
    }

    public void calculate(OkHttpClient client, Subfund subfund, int numberOfMonths) {
        Result result = resultGetter.get(client, subfund, numberOfMonths);
        List<Data> dataList = arithmeticMovingAverage.calculate(result, subfund.getNumberOfElements());

        dataList.stream()
                .skip(dataList.size() - subfund.getNumberOfElements())
                .forEach(this::log);
    }

    private void log(Data data) {
        logger.info("{} | {} | {} | {}", data.getDate(), data.getValue().setScale(2, RoundingMode.HALF_UP), data.getAverage(), data.getDeviation());
    }
}
