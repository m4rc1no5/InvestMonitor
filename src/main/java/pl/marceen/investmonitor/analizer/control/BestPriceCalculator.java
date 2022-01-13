package pl.marceen.investmonitor.analizer.control;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Marcin Zaremba
 */
public class BestPriceCalculator {
    public BigDecimal calculate(BigDecimal average, BigDecimal threshold) {
        return average.multiply(threshold).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).add(average);
    }
}
