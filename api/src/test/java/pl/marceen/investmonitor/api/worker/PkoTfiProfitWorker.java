package pl.marceen.investmonitor.api.worker;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.api.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.api.analizer.control.ProfitCalculator;
import pl.marceen.investmonitor.api.analizer.entity.Data;
import pl.marceen.investmonitor.api.analizer.entity.Result;
import pl.marceen.investmonitor.api.network.control.HttpClientProducer;
import pl.marceen.investmonitor.api.network.control.HttpExecutor;
import pl.marceen.investmonitor.api.pkotfi.control.RequestBuilder;
import pl.marceen.investmonitor.api.pkotfi.control.ResultMapper;
import pl.marceen.investmonitor.api.pkotfi.control.UrlBuilder;
import pl.marceen.investmonitor.api.pkotfi.entity.FundResponse;
import pl.marceen.investmonitor.api.pkotfi.entity.Subfund;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
@ExtendWith(MockitoExtension.class)
public class PkoTfiProfitWorker {
    private static final Logger logger = LoggerFactory.getLogger(PkoTfiProfitWorker.class);

    private static final BigDecimal AMOUNT = new BigDecimal(10000);

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private HttpExecutor<FundResponse> httpExecutor;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private UrlBuilder urlBuilder;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private RequestBuilder requestBuilder;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ResultMapper resultMapper;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ArithmeticMovingAverage arithmeticMovingAverage;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ProfitCalculator profitCalculator;

    @InjectMocks
    private ResultGetter resultGetter;

    @Test
    void work() {
        OkHttpClient client = new HttpClientProducer().produce();
        Arrays.stream(Subfund.values()).forEach(subfund -> showProfit(client, subfund));
    }

    private void showProfit(OkHttpClient client, Subfund subfund) {
        Result result = resultGetter.get(client, subfund);

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
