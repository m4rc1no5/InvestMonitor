package pl.marceen.investmonitor.pkotfi.control;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.boundary.AbstractResultGetter;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.investment.entity.InstrumentInterface;
import pl.marceen.investmonitor.network.control.HttpExecutor;
import pl.marceen.investmonitor.pkotfi.entity.FundResponse;

import java.time.LocalDateTime;

/**
 * @author Marcin Zaremba
 */
public class ResultGetter extends AbstractResultGetter {
    private static final Logger logger = LoggerFactory.getLogger(ResultGetter.class);

    private final UrlBuilder urlBuilder;

    private final RequestBuilder requestBuilder;

    private final HttpExecutor<FundResponse> httpExecutor;

    private final ResultMapper resultMapper;

    public ResultGetter() {
        urlBuilder = new UrlBuilder();
        requestBuilder = new RequestBuilder();
        httpExecutor = new HttpExecutor<>();
        resultMapper = new ResultMapper();
    }

    public Result get(OkHttpClient client, InstrumentInterface instrument, int numberOfMonths) {
        printInstrumentData(logger, instrument, numberOfMonths);

        LocalDateTime now = LocalDateTime.now();
        String url = urlBuilder.build(instrument, now.minusMonths(numberOfMonths), now);
        Request request = requestBuilder.build(url);

        FundResponse fundResponse = httpExecutor.execute(FundResponse.class, client, request);
        return resultMapper.map(fundResponse.getDataList());
    }
}
