package pl.marceen.investmonitor.gpw.control;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.gpw.entity.Instrument;
import pl.marceen.investmonitor.gpw.entity.Response;
import pl.marceen.investmonitor.network.control.HttpExecutor;

import java.time.LocalDateTime;

/**
 * @author Marcin Zaremba
 */
public class ResultGetter {
    private static final Logger logger = LoggerFactory.getLogger(ResultGetter.class);

    private final UrlBuilder urlBuilder;
    private final HttpExecutor<Response> httpExecutor;
    private final ResultMapper resultMapper;

    public ResultGetter(UrlBuilder urlBuilder, HttpExecutor<Response> httpExecutor, ResultMapper resultMapper) {
        this.urlBuilder = urlBuilder;
        this.httpExecutor = httpExecutor;
        this.resultMapper = resultMapper;
    }

    public Result get(OkHttpClient client, Instrument instrument, int numberOfMonths) {
        LocalDateTime now = LocalDateTime.now();
        String url = urlBuilder.build(instrument, now.minusMonths(numberOfMonths), now);

        Response response = httpExecutor.execute(Response.class, client, buildRequest(url));
        logger.info("Response: {}", response);

        return resultMapper.map(response);
    }

    private Request buildRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }
}
