package pl.marceen.investmonitor.pkotfi.entity;

import pl.marceen.investmonitor.pkotfi.control.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Marcin Zaremba
 */
public class Data {

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;
    private BigDecimal roi;
    private String unit;
    private BigDecimal value;

    public LocalDate getDate() {
        return date;
    }

    public Data setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public BigDecimal getRoi() {
        return roi;
    }

    public Data setRoi(BigDecimal roi) {
        this.roi = roi;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Data setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Data setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "date=" + date +
                ", roi=" + roi +
                ", unit='" + unit + '\'' +
                ", value=" + value +
                '}';
    }
}
