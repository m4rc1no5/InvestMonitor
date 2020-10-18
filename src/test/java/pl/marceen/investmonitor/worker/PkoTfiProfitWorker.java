package pl.marceen.investmonitor.worker;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.api.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.api.analizer.control.ProfitCalculator;
import pl.marceen.investmonitor.api.analizer.entity.Data;
import pl.marceen.investmonitor.api.analizer.entity.Result;
import pl.marceen.investmonitor.api.network.control.HttpClientProducer;
import pl.marceen.investmonitor.api.network.control.HttpExecutor;
import pl.marceen.investmonitor.api.pkotfi.control.RequestBuilder;
import pl.marceen.investmonitor.api.pkotfi.control.ResultGetter;
import pl.marceen.investmonitor.api.pkotfi.control.ResultMapper;
import pl.marceen.investmonitor.api.pkotfi.control.UrlBuilder;
import pl.marceen.investmonitor.api.pkotfi.entity.Subfund;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class PkoTfiProfitWorker {
    private static final Logger logger = LoggerFactory.getLogger(PkoTfiProfitWorker.class);

    private static final BigDecimal AMOUNT = new BigDecimal(10000);

    private ResultGetter resultGetter;
    private ProfitCalculator profitCalculator;
    private ArithmeticMovingAverage arithmeticMovingAverage;

    @Test
    void work() {
        OkHttpClient client = new HttpClientProducer().produce();
        resultGetter = new ResultGetter(new UrlBuilder(), new RequestBuilder(), new HttpExecutor<>(), new ResultMapper());
        profitCalculator = new ProfitCalculator();
        arithmeticMovingAverage = new ArithmeticMovingAverage();

        Arrays.stream(Subfund.values()).forEach(subfund -> showProfit(client, subfund));
    }

    private void showProfit(OkHttpClient client, Subfund subfund) {
        Result result = resultGetter.get(client, subfund, 60);

        BigDecimal firstValue = result.getDataList().get(0).getValue();
        logger.info("First value: {}", firstValue);
        BigDecimal point = AMOUNT.divide(firstValue, 10, RoundingMode.CEILING);
        logger.info("Point: {}", point);
        BigDecimal lastValue = result.getDataList().get(result.dataList.size() - 1).getValue();
        logger.info("Result without strategy: {}", point.multiply(lastValue));

        List<Data> dataList = arithmeticMovingAverage.calculate(result, subfund.getNumberOfElements());
        BigDecimal profit = profitCalculator.calculate(dataList, AMOUNT, subfund.getEntry(), subfund.getExit());
        logger.info("Result with strategy: {}", profit);
    }
}