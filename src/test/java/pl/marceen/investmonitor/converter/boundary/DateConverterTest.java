package pl.marceen.investmonitor.converter.boundary;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Marcin Zaremba
 */
class DateConverterTest {
    @Test
    void convertFromTimestamp() {
        // given
        long timestamp = 1581980400L;

        // when
        LocalDate result = DateConverter.convertFromTimestamp(timestamp);

        // then
        assertThat(result.toString()).isEqualTo("2020-02-18");
    }
}