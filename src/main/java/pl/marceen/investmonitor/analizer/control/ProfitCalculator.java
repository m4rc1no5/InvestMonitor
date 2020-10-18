package pl.marceen.investmonitor.analizer.control;

import pl.marceen.investmonitor.analizer.entity.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class ProfitCalculator {
    public BigDecimal calculate(List<Data> dataList, BigDecimal amount, BigDecimal enter, BigDecimal exit) {
        BigDecimal value = dataList.get(0).getValue();
        BigDecimal points = amount.divide(value, 3, RoundingMode.CEILING);
        boolean in = true;

        for (Data data : dataList) {
            if (data.getDeviation().compareTo(exit) < 0 && in) {
                // logger.info("===");
                // logger.info("Escaping on {} - deviation: {}", element.getDate(), deviation);
                amount = data.getValue().multiply(points);
                // logger.info("Amount: {}", amount);
                // logger.info("Points: {}", points);
                in = false;
            }

            if (data.getDeviation().compareTo(enter) > 0 && !in) {
                // logger.info("===");
                // logger.info("Entering on {} - deviation: {}", element.getDate(), deviation);
                points = amount.divide(data.getValue(), 3, RoundingMode.CEILING);
                // logger.info("Amount: {}", amount);
                // logger.info("Points: {}", points);
                in = true;
            }
        }

        return dataList.get(dataList.size() - 1).getValue().multiply(points);
    }
}
