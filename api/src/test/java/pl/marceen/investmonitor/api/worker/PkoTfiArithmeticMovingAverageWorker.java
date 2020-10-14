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
import java.util.Arrays;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
@ExtendWith(MockitoExtension.class)
public class PkoTfiArithmeticMovingAverageWorker {
    private static final Logger logger = LoggerFactory.getLogger(PkoTfiProfitWorker.class);

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

    @InjectMocks
    private ResultGetter resultGetter;

    @Test
    void work() {
        OkHttpClient client = new HttpClientProducer().produce();

        Arrays.stream(Subfund.values()).forEach(subfund -> calculate(client, subfund));
    }

    public void calculate(OkHttpClient client, Subfund subfund) {
        Result result = resultGetter.get(client, subfund);
        List<Data> dataList = arithmeticMovingAverage.calculate(result, subfund.getNumberOfElements());

        dataList.stream()
                .skip(dataList.size() - subfund.getNumberOfElements())
                .forEach(this::log);
    }

    private void log(Data data) {
        logger.info("{} | {} | {} | {}", data.getDate(), data.getValue().setScale(2, RoundingMode.HALF_UP), data.getAverage(), data.getDeviation());
    }
}
