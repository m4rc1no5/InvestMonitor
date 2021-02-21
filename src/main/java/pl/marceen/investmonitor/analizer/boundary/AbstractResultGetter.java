package pl.marceen.investmonitor.analizer.boundary;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.investment.entity.InstrumentInterface;

/**
 * @author Marcin Zaremba
 */
public abstract class AbstractResultGetter {
    public abstract Result get(OkHttpClient client, InstrumentInterface subfund, int numberOfMonths);

    protected void printInstrumentData(Logger logger, InstrumentInterface instrument, int numberOfMonths) {
        logger.info("========");
        logger.info("Instrument: {} - {}", instrument, instrument.getInstrumentName());
        logger.info("Number of months: {}", numberOfMonths);
        logger.info("Number of elements: {}", instrument.getNumberOfElements());
        logger.info("Entry: {}", instrument.getEntry());
        logger.info("Exit: {}", instrument.getExit());
    }
}
