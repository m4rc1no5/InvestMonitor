package pl.marceen.investmonitor.gpw.control;

import okhttp3.OkHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.gpw.entity.Instrument;
import pl.marceen.investmonitor.network.control.HttpClientProducer;

/**
 * @author Marcin Zaremba
 */
class ResultGetterTest {
    private ResultGetter sut;

    private OkHttpClient client;

    @BeforeEach
    void setUp() {
        sut = new ResultGetter();
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