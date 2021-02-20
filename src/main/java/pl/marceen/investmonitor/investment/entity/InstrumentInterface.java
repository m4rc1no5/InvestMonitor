package pl.marceen.investmonitor.investment.entity;

import java.math.BigDecimal;

/**
 * @author Marcin Zaremba
 */
public interface InstrumentInterface {
    int getNumberOfElements();
    BigDecimal getEntry();
    BigDecimal getExit();
}
