package pl.marceen.investmonitor.api.analizer.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Marcin Zaremba
 */
public class Data {
    private LocalDate date;
    private BigDecimal value;
    private BigDecimal average;
    private BigDecimal deviation;

    public LocalDate getDate() {
        return date;
    }

    public Data setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Data setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public Data setAverage(BigDecimal average) {
        this.average = average;
        return this;
    }

    public BigDecimal getDeviation() {
        return deviation;
    }

    public Data setDeviation(BigDecimal deviation) {
        this.deviation = deviation;
        return this;
    }

    @Override
    public String toString() {
        return "Data{" +
                "date=" + date +
                ", value=" + value +
                ", average=" + average +
                ", deviation=" + deviation +
                '}';
    }
}
