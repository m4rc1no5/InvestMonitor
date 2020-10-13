package pl.marceen.investmonitor.api.pkotfi.control;

import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.api.pkotfi.entity.Subfund;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Marcin Zaremba
 */
public class UrlBuilder {
    private static final Logger logger = LoggerFactory.getLogger(UrlBuilder.class);

    private static final String HOST = "www.pkotfi.pl";

    public String build(Subfund subfund, LocalDateTime fromDate, LocalDateTime toDate) {
        String url = new HttpUrl.Builder()
                .scheme("https")
                .host(HOST)
                .addPathSegment("_ajax")
                .addPathSegment("rest")
                .addPathSegment("v2")
                .addPathSegment("tfi")
                .addPathSegment("fund")
                .addPathSegment(subfund.getId())
                .addPathSegment("values")
                .addQueryParameter("format", "json")
                .addQueryParameter("ajax", "1")
                .addQueryParameter("lang", "en")
                .addQueryParameter("date__gte", fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .addQueryParameter("date__lte", toDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .addQueryParameter("division", "daily")
                .addQueryParameter("unit", "A")
                .build()
                .toString();
        logger.debug("Url: {}", url);

        return url;
    }
}
