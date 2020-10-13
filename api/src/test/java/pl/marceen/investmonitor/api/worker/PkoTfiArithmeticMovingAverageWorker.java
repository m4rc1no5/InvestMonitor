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

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
@ExtendWith(MockitoExtension.class)
public class PkoTfiArithmeticMovingAverageWorker {
    private static final Logger logger = LoggerFactory.getLogger(PkoTfiProfitWorker.class);

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

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ProfitCalculator profitCalculator;

    @Test
    void work() {
        HttpClientProducer httpClientProducer = new HttpClientProducer();
        OkHttpClient client = httpClientProducer.produce();

        Arrays.stream(Subfund.values()).forEach(subfund -> calculate(client, subfund));
    }

    public void calculate(OkHttpClient client, Subfund subfund) {
        logger.info("========");
        logger.info("Subfund: {}", subfund);

        LocalDateTime now = LocalDateTime.now();
        String url = urlBuilder.build(subfund, now.minusYears(5), now);
        Request request = requestBuilder.build(url);

        FundResponse fundResponse = (FundResponse) httpExecutor.execute(FundResponse.class, client, request);
        Result result = resultMapper.map(fundResponse.getDataList());
        List<Data> dataList = arithmeticMovingAverage.calculate(result, 23);

        dataList.stream()
                .skip(dataList.size() - 20)
                .forEach(this::log);
    }

    private void log(Data data) {
        logger.info("{} | {} | {} | {}", data.getDate(), data.getValue().setScale(2, RoundingMode.HALF_UP), data.getAverage(), data.getDeviation());
    }
}
