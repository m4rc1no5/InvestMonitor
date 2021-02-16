package pl.marceen.investmonitor.gpw.control;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.marceen.investmonitor.converter.boundary.JsonConverter;
import pl.marceen.investmonitor.gpw.entity.Instrument;
import pl.marceen.investmonitor.network.control.HttpClientProducer;
import pl.marceen.investmonitor.network.control.HttpExecutor;

/**
 * @author Marcin Zaremba
 */
class ResultGetterTest {
    private ResultGetter sut;

    private OkHttpClient client;

    @BeforeEach
    void setUp() {
        UrlBuilder urlBuilder = new UrlBuilder(new RequestBuilder(), new JsonConverter());

        sut = new ResultGetter(urlBuilder, new HttpExecutor<>());

        client = new HttpClientProducer().produce();
    }

    @Test
    void get() {
        // given


        // when
        sut.get(client, Instrument.ETFSP500, 6);

        // then
    }
}