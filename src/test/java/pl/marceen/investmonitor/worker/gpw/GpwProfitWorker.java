package pl.marceen.investmonitor.worker.gpw;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.analizer.boundary.ProfitCalculator;
import pl.marceen.investmonitor.analizer.entity.Data;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.gpw.control.ResultGetter;
import pl.marceen.investmonitor.gpw.entity.Instrument;
import pl.marceen.investmonitor.network.control.HttpClientProducer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class GpwProfitWorker {
    private static final Logger logger = LoggerFactory.getLogger(GpwProfitWorker.class);

    private static final BigDecimal AMOUNT = new BigDecimal(10000);

    private ResultGetter resultGetter;
    private ProfitCalculator profitCalculator;
    private ArithmeticMovingAverage arithmeticMovingAverage;

    @Test
    void work() {
        OkHttpClient client = new HttpClientProducer().produce();
        resultGetter = new ResultGetter();
        profitCalculator = new ProfitCalculator();
        arithmeticMovingAverage = new ArithmeticMovingAverage();

        Arrays.stream(Instrument.values()).forEach(instrument -> showProfit(client, instrument));
    }

    private void showProfit(OkHttpClient client, Instrument instrument) {
        Result result = resultGetter.get(client, instrument, 60);

        BigDecimal firstValue = result.getDataList().get(0).getValue();
        logger.info("First value: {}", firstValue);
        BigDecimal point = AMOUNT.divide(firstValue, 10, RoundingMode.CEILING);
        logger.info("Point: {}", point);
        BigDecimal lastValue = result.getDataList().get(result.dataList.size() - 1).getValue();
        logger.info("Result without strategy: {}", point.multiply(lastValue));

        List<Data> dataList = arithmeticMovingAverage.calculate(result, instrument.getNumberOfElements());
        BigDecimal profit = profitCalculator.calculate(dataList, AMOUNT, instrument.getEntry(), instrument.getExit(), 1);
        logger.info("Result with strategy: {}", profit);
    }
}
