package pl.marceen.investmonitor.api.pkotfi.control;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.api.network.control.HttpExecutor;
import pl.marceen.investmonitor.api.pkotfi.entity.FundResponse;
import pl.marceen.investmonitor.api.pkotfi.entity.Subfund;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * @author Marcin Zaremba
 */
public class PkoTfiSender {
    private static final Logger logger = LoggerFactory.getLogger(PkoTfiSender.class);

    @Inject
    private OkHttpClient okHttpClient;

    @Inject
    private HttpExecutor<FundResponse> httpExecutor;

    @Inject
    private UrlBuilder urlBuilder;

    @Inject
    private RequestBuilder requestBuilder;

    public FundResponse getFundResponse(Subfund subfund) {
        logger.info("Getting data for {}", subfund);

        LocalDateTime now = LocalDateTime.now();

        String url = urlBuilder.build(subfund, now.minusYears(5), now);
        Request request = requestBuilder.build(url);

        FundResponse fundResponse = httpExecutor.execute(FundResponse.class, okHttpClient, request);
        logger.info("Result: {}", fundResponse);

        return fundResponse;
    }
}
