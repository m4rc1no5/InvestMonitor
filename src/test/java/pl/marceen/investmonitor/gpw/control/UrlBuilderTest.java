package pl.marceen.investmonitor.gpw.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.marceen.investmonitor.converter.boundary.JsonConverter;
import pl.marceen.investmonitor.gpw.entity.Instrument;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Marcin Zaremba
 */
class UrlBuilderTest {
    private UrlBuilder sut;

    @BeforeEach
    void setUp() {
        sut = new UrlBuilder(
                new RequestBuilder(),
                new JsonConverter()
        );
    }

    @Test
    void build() {
        // when
        String result = sut.build(Instrument.ETFSP500, LocalDateTime.parse("2021-01-01T00:00:00"), LocalDateTime.parse("2021-02-16T00:00:00"));

        // then
        assertThat(result).isEqualTo("https://www.gpw.pl/chart-json.php?req=%5B%7B%22from%22%3A%222021-01-01%22%2C%22isin%22%3A%22LU0496786574%22%2C%22mode%22%3A%22RANGE%22%2C%22to%22%3A%222021-02-16%22%7D%5D");
    }
}