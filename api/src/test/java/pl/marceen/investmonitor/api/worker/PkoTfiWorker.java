package pl.marceen.investmonitor.api.worker;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.api.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.api.analizer.entity.Result;
import pl.marceen.investmonitor.api.network.control.HttpClientProducer;
import pl.marceen.investmonitor.api.network.control.HttpExecutor;
import pl.marceen.investmonitor.api.pkotfi.control.RequestBuilder;
import pl.marceen.investmonitor.api.pkotfi.control.ResultMapper;
import pl.marceen.investmonitor.api.pkotfi.control.UrlBuilder;
import pl.marceen.investmonitor.api.pkotfi.entity.FundResponse;
import pl.marceen.investmonitor.api.pkotfi.entity.Data;
import pl.marceen.investmonitor.api.pkotfi.entity.Subfund;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * @author Marcin Zaremba
 */
@ExtendWith(MockitoExtension.class)
public class PkoTfiWorker {
    private static final Logger logger = LoggerFactory.getLogger(PkoTfiWorker.class);

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private HttpExecutor httpExecutor;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private UrlBuilder urlBuilder;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private RequestBuilder requestBuilder;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ResultMapper resultMapper;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ArithmeticMovingAverage arithmeticMovingAverage;

    @Test
    void work() {
        HttpClientProducer httpClientProducer = new HttpClientProducer();
        OkHttpClient client = httpClientProducer.produce();

        LocalDateTime now = LocalDateTime.now();

        String url = urlBuilder.build(Subfund.GLOBAL_TECHNOLOGY_AND_INNOVATION, now.minusYears(5), now);
        Request request = requestBuilder.build(url);

        FundResponse fundResponse = (FundResponse) httpExecutor.execute(FundResponse.class, client, request);
        Result result = resultMapper.map(fundResponse.getDataList());

        // logger.info("Result: {}", result);
        BigDecimal amount = new BigDecimal(10000);
        BigDecimal firstValue = result.getDataList().get(0).getValue();
        logger.info("First value: {}", firstValue);
        BigDecimal point = amount.divide(firstValue, 10, RoundingMode.CEILING);
        logger.info("Point: {}", point);
        BigDecimal lastValue = result.getDataList().get(result.dataList.size()-1).getValue();
        logger.info("Result: {}", point.multiply(lastValue));


        arithmeticMovingAverage.calculate(result, 20, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 21, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 22, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 23, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 24, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 25, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 26, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 27, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 28, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 29, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 30, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 31, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 32, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 33, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 34, new BigDecimal("0.6"), new BigDecimal("-0.6"));
        arithmeticMovingAverage.calculate(result, 35, new BigDecimal("0.6"), new BigDecimal("-0.6"));
    }

    private void logResult(Data data) {
        logger.info("Date: {}, ROI: {}, Value: {}", data.getDate(), data.getRoi(), data.getValue());
    }
}
