package pl.marceen.investmonitor.analizer.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.entity.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class ProfitCalculator {
    private static final Logger logger = LoggerFactory.getLogger(ProfitCalculator.class);

    public BigDecimal calculate(List<Data> dataList, BigDecimal amount, BigDecimal entry, BigDecimal exit) {
        BigDecimal value = dataList.get(0).getValue();
        BigDecimal points = amount.divide(value, 3, RoundingMode.CEILING);
        boolean in = false;
        boolean startTransaction = false;
        int x = 0;
        Action action = Action.ENTER;

        for (Data data : dataList) {
            if (startTransaction && x < 3) {
                x++;
                continue;
            }

            if (startTransaction && action == Action.EXIT) {
                logger.debug("Escaping on {} - deviation: {}", data.getDate(), data.getDeviation());
                amount = data.getValue().multiply(points);
                startTransaction = false;
                x = 0;
                continue;
            }

            if (startTransaction) {
                logger.debug("Entering on {} - deviation: {}", data.getDate(), data.getDeviation());
                points = amount.divide(data.getValue(), 3, RoundingMode.CEILING);
                startTransaction = false;
                x = 0;
                continue;
            }

            if (data.getDeviation().compareTo(exit) < 0 && in) {
                logger.debug("===");
                logger.debug("Start transaction for exit on {} - deviation: {}", data.getDate(), data.getDeviation());
                startTransaction = true;
                action = Action.EXIT;
                in = false;
                continue;
            }

            if (data.getDeviation().compareTo(entry) > 0 && !in) {
                logger.debug("===");
                logger.debug("Start transaction for enter on {} - deviation: {}", data.getDate(), data.getDeviation());
                startTransaction = true;
                action = Action.ENTER;
                in = true;
                x = 0;
            }
        }

        return dataList.get(dataList.size() - 1).getValue().multiply(points);
    }

    public enum Action {
        ENTER, EXIT
    }
}
