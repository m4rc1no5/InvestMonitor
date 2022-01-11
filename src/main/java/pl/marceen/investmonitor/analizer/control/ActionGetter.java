package pl.marceen.investmonitor.analizer.control;

import pl.marceen.investmonitor.analizer.entity.Action;

import java.math.BigDecimal;

/**
 * @author Marcin Zaremba
 */
public class ActionGetter {
    public Action get(BigDecimal deviation, BigDecimal exit, BigDecimal entry) {
        if (deviation.compareTo(exit) < 0) {
            return Action.SELL;
        }

        if (deviation.compareTo(entry) > 0) {
            return Action.BUY;
        }

        return Action.STAY;
    }
}
