package pl.marceen.investmonitor.gpw.control;

import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.converter.boundary.JsonConverter;
import pl.marceen.investmonitor.gpw.entity.Request;
import pl.marceen.investmonitor.investment.entity.InstrumentInterface;

import java.time.LocalDateTime;

/**
 * @author Marcin Zaremba
 */
public class UrlBuilder {
    private static final Logger logger = LoggerFactory.getLogger(UrlBuilder.class);

    private final RequestBuilder requestBuilder;
    private final JsonConverter jsonConverter;

    public UrlBuilder() {
        requestBuilder = new RequestBuilder();
        jsonConverter = new JsonConverter();
    }

    public String build(InstrumentInterface instrument, LocalDateTime fromDate, LocalDateTime toDate) {
        String url = new HttpUrl.Builder()
                .scheme("https")
                .host("www.gpw.pl")
                .addPathSegment("chart-json.php")
                .addQueryParameter("req", getReq(instrument, fromDate, toDate))
                .build()
                .toString();
        logger.info("Url: {}", url);

        return url;
    }

    private String getReq(InstrumentInterface instrument, LocalDateTime fromDate, LocalDateTime toDate) {
        Request request = requestBuilder.build(instrument, fromDate, toDate);
        return "[" + jsonConverter.toJson(request) + "]";
    }
}
