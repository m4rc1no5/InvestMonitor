package pl.marceen.investmonitor.gpw.control;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.gpw.entity.Instrument;
import pl.marceen.investmonitor.network.control.HttpExecutor;

import java.time.LocalDateTime;

/**
 * @author Marcin Zaremba
 */
public class ResultGetter {

    private final UrlBuilder urlBuilder;
    private final HttpExecutor<String> httpExecutor;

    public ResultGetter(UrlBuilder urlBuilder, HttpExecutor<String> httpExecutor) {
        this.urlBuilder = urlBuilder;
        this.httpExecutor = httpExecutor;
    }

    public Result get(OkHttpClient client, Instrument instrument, int numberOfMonths) {
        LocalDateTime now = LocalDateTime.now();
        String url = urlBuilder.build(instrument, now.minusMonths(numberOfMonths), now);

        httpExecutor.execute(String.class, client, buildRequest(url));

        return null;
    }

    private Request buildRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }
}
