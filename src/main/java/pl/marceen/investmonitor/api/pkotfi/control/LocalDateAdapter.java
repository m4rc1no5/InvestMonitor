package pl.marceen.investmonitor.api.pkotfi.control;

import org.slf4j.Logger;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Marcin Zaremba
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private static final Logger logger = getLogger(LocalDateAdapter.class);

    @Override
    public LocalDate unmarshal(String localDateString) {
        logger.info("Try to unmarshal {}", localDateString);

        return LocalDate.parse(localDateString, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String marshal(LocalDate localDate) {
        return DateTimeFormatter.ISO_DATE.format(localDate);
    }
}
