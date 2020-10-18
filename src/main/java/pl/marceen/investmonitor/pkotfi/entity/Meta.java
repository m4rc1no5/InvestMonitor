package pl.marceen.investmonitor.pkotfi.entity;

/**
 * @author Marcin Zaremba
 */
public class Meta {
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public Meta setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "currency='" + currency + '\'' +
                '}';
    }
}
