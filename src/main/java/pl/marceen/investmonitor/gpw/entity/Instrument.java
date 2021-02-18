package pl.marceen.investmonitor.gpw.entity;

import java.math.BigDecimal;

/**
 * @author Marcin Zaremba
 */
public enum Instrument {
    ETFSP500("LU0496786574", "ETF S&P500", 20, "2.8", "-4.0", true),
    ETFDAX("LU0252633754", "ETF DAX", 20, "1.2", "-3.8", true),
    BETAM40TR("PLBETF400025", "ETF MWIG40TR", 35, "2.6", "-2.0", true);

    private final String id;
    private final String fullName;
    private final int numberOfElements;
    private final BigDecimal entry;
    private final BigDecimal exit;
    private final boolean active;

    Instrument(String id, String fullName, int numberOfElements, String entry, String exit, boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.numberOfElements = numberOfElements;
        this.entry = new BigDecimal(entry);
        this.exit = new BigDecimal(exit);
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public BigDecimal getEntry() {
        return entry;
    }

    public BigDecimal getExit() {
        return exit;
    }

    public boolean isActive() {
        return active;
    }
}
