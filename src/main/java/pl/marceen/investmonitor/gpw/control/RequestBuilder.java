package pl.marceen.investmonitor.gpw.control;

import pl.marceen.investmonitor.gpw.entity.Request;
import pl.marceen.investmonitor.investment.entity.InstrumentInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Marcin Zaremba
 */
public class RequestBuilder {
    public Request build(InstrumentInterface instrument, LocalDateTime fromDate, LocalDateTime toDate) {
        return new Request()
                .setIsin(instrument.getId())
                .setMode("RANGE")
                .setFrom(fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .setTo(toDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
