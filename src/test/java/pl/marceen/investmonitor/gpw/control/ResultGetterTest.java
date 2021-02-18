package pl.marceen.investmonitor.gpw.control;

import okhttp3.OkHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.marceen.investmonitor.analizer.entity.Result;
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

        sut = new ResultGetter(urlBuilder, new HttpExecutor<>(), new ResultMapper());

        client = new HttpClientProducer().produce();
    }

    @Test
    @Disabled
    void get() {
        // when
        Result result = sut.get(client, Instrument.ETFSP500, 6);

        // then
        Assertions.assertThat(result.getDataList().size()).isEqualTo(2);
    }
}