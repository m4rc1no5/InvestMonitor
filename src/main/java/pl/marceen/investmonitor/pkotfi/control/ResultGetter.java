package pl.marceen.investmonitor.pkotfi.control;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.network.control.HttpExecutor;
import pl.marceen.investmonitor.pkotfi.entity.FundResponse;
import pl.marceen.investmonitor.pkotfi.entity.Subfund;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * @author Marcin Zaremba
 */
public class ResultGetter {
    private static final Logger logger = LoggerFactory.getLogger(ResultGetter.class);

    @Inject
    private final UrlBuilder urlBuilder;

    @Inject
    private final RequestBuilder requestBuilder;

    @Inject
    private final HttpExecutor<FundResponse> httpExecutor;

    @Inject
    private final ResultMapper resultMapper;

    public ResultGetter(UrlBuilder urlBuilder, RequestBuilder requestBuilder, HttpExecutor<FundResponse> httpExecutor, ResultMapper resultMapper) {
        this.urlBuilder = urlBuilder;
        this.requestBuilder = requestBuilder;
        this.httpExecutor = httpExecutor;
        this.resultMapper = resultMapper;
    }

    public Result get(OkHttpClient client, Subfund subfund, int numberOfMonths) {
        logger.info("========");
        logger.info("Subfund: {} - {} ({})", subfund, subfund.getSubfundName(), subfund.getSubfundNameInPolish());
        logger.info("Number of months: {}", numberOfMonths);
        logger.info("Number of elements: {}", subfund.getNumberOfElements());
        logger.info("Entry: {}", subfund.getEntry());
        logger.info("Exit: {}", subfund.getExit());

        LocalDateTime now = LocalDateTime.now();
        String url = urlBuilder.build(subfund, now.minusMonths(numberOfMonths), now);
        Request request = requestBuilder.build(url);

        FundResponse fundResponse = httpExecutor.execute(FundResponse.class, client, request);
        return resultMapper.map(fundResponse.getDataList());
    }
}
