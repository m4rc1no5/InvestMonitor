package pl.marceen.investmonitor.worker.pkotfi;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.analizer.boundary.ProfitCalculator;
import pl.marceen.investmonitor.analizer.entity.Data;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.network.control.HttpClientProducer;
import pl.marceen.investmonitor.pkotfi.control.ResultGetter;
import pl.marceen.investmonitor.pkotfi.entity.Subfund;

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

    private static ResultGetter resultGetter;
    private static ProfitCalculator profitCalculator;
    private static ArithmeticMovingAverage arithmeticMovingAverage;

    public static void main(String[] args) {
        resultGetter = new ResultGetter();
        profitCalculator = new ProfitCalculator();
        arithmeticMovingAverage = new ArithmeticMovingAverage();

        OkHttpClient client = new HttpClientProducer().produce();
        Arrays.stream(Subfund.values())
                .forEach(subfund -> showProfit(client, subfund));
    }

    private static void showProfit(OkHttpClient client, Subfund subfund) {
        Result result = resultGetter.get(client, subfund, 60);

        BigDecimal firstValue = result.getDataList().get(0).getValue();
        logger.info("First value: {}", firstValue);
        BigDecimal point = AMOUNT.divide(firstValue, 10, RoundingMode.CEILING);
        logger.info("Point: {}", point);
        BigDecimal lastValue = result.getDataList().get(result.dataList.size() - 1).getValue();
        logger.info("Result without strategy: {}", point.multiply(lastValue));

        List<Data> dataList = arithmeticMovingAverage.calculate(result, subfund.getNumberOfElements());
        BigDecimal profit = profitCalculator.calculate(dataList, AMOUNT, subfund.getEntry(), subfund.getExit(), 3);
        logger.info("Result with strategy: {}", profit);
    }
}
