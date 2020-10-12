package pl.marceen.investmonitor.api.pkotfi.control;

import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Marcin Zaremba
 */
public class RequestBuilder {
    private static final Logger logger = LoggerFactory.getLogger(RequestBuilder.class);

    public Request build(String url) {
        logger.info("Building request for url: {}", url);

        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }
}
