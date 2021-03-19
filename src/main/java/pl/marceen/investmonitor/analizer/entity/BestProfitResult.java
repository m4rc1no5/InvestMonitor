package pl.marceen.investmonitor.analizer.entity;

import java.math.BigDecimal;

/**
 * @author Marcin Zaremba
 */
public class BestProfitResult {
    private final BigDecimal profit;
    private final int numberOfElements;
    private final BigDecimal entry;
    private final BigDecimal exit;

    public BestProfitResult(BigDecimal profit, int numberOfElements, BigDecimal entry, BigDecimal exit) {
        this.profit = profit;
        this.numberOfElements = numberOfElements;
        this.entry = entry;
        this.exit = exit;
    }

    public BigDecimal getProfit() {
        return profit;
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

    @Override
    public String toString() {
        return "BestProfitResult{" +
                "profit=" + profit +
                ", numberOfElements=" + numberOfElements +
                ", entry=" + entry +
                ", exit=" + exit +
                '}';
    }
}
