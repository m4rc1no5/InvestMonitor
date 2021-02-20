package pl.marceen.investmonitor.converter.boundary;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Marcin Zaremba
 */
public final class DateConverter {
    private DateConverter() {
    }

    public static LocalDate convertFromTimestamp(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("Europe/Warsaw")).toLocalDate();
    }
}
