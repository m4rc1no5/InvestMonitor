package pl.marceen.investmonitor.investment.entity;

import java.math.BigDecimal;

/**
 * @author Marcin Zaremba
 */
public interface InstrumentInterface {
    String getId();
    String getInstrumentName();
    int getNumberOfElements();
    BigDecimal getEntry();
    BigDecimal getExit();
    boolean isActive();
}
