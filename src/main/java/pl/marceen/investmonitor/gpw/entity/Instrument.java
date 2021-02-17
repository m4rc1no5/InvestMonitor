package pl.marceen.investmonitor.gpw.entity;

/**
 * @author Marcin Zaremba
 */
public enum Instrument {
    ETFSP500("LU0496786574", "ETF S&P500"),
    ETFDAX("LU0252633754", "ETF DAX"),
    BETAM40TR("PLBETF400025", "ETF MWIG40TR");

    private final String id;
    private final String fullName;

    Instrument(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}
