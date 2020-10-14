package pl.marceen.investmonitor.api.worker;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.api.analizer.entity.Result;
import pl.marceen.investmonitor.api.network.control.HttpExecutor;
import pl.marceen.investmonitor.api.pkotfi.control.RequestBuilder;
import pl.marceen.investmonitor.api.pkotfi.control.ResultMapper;
import pl.marceen.investmonitor.api.pkotfi.control.UrlBuilder;
import pl.marceen.investmonitor.api.pkotfi.entity.FundResponse;
import pl.marceen.investmonitor.api.pkotfi.entity.Subfund;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * @author Marcin Zaremba
 */
public class ResultGetter {
    private static final Logger logger = LoggerFactory.getLogger(ResultGetter.class);

    @Inject
    private UrlBuilder urlBuilder;

    @Inject
    private RequestBuilder requestBuilder;

    @Inject
    private HttpExecutor<FundResponse> httpExecutor;

    @Inject
    private ResultMapper resultMapper;

    public Result get(OkHttpClient client, Subfund subfund) {
        logger.info("\n========");
        logger.info("Subfund: {} - {} ({})", subfund, subfund.getSubfundName(), subfund.getSubfundNameInPolish());
        logger.info("Number of elements: {}", subfund.getNumberOfElements());
        logger.info("Entry: {}", subfund.getEntry());
        logger.info("Exit: {}", subfund.getExit());

        LocalDateTime now = LocalDateTime.now();
        String url = urlBuilder.build(subfund, now.minusYears(5), now);
        Request request = requestBuilder.build(url);

        FundResponse fundResponse = httpExecutor.execute(FundResponse.class, client, request);
        return resultMapper.map(fundResponse.getDataList());
    }
}
