package pl.marceen.investmonitor.analizer.entity;

import java.math.BigDecimal;

/**
 * @author Marcin Zaremba
 */
public class BestProfitData {
    private BigDecimal minEntry;
    private BigDecimal maxEntry;
    private BigDecimal entryStep;

    private BigDecimal minExit;
    private BigDecimal maxExit;
    private BigDecimal exitStep;

    private BigDecimal amountInvested;

    private int minNumberOfElements;
    private int maxNumberOfElements;
    private int numberOfMonths;
    private int delay;

    public BigDecimal getMinEntry() {
        return minEntry;
    }

    public BestProfitData setMinEntry(BigDecimal minEntry) {
        this.minEntry = minEntry;
        return this;
    }

    public BigDecimal getMaxEntry() {
        return maxEntry;
    }

    public BestProfitData setMaxEntry(BigDecimal maxEntry) {
        this.maxEntry = maxEntry;
        return this;
    }

    public BigDecimal getEntryStep() {
        return entryStep;
    }

    public BestProfitData setEntryStep(BigDecimal entryStep) {
        this.entryStep = entryStep;
        return this;
    }

    public BigDecimal getMinExit() {
        return minExit;
    }

    public BestProfitData setMinExit(BigDecimal minExit) {
        this.minExit = minExit;
        return this;
    }

    public BigDecimal getMaxExit() {
        return maxExit;
    }

    public BestProfitData setMaxExit(BigDecimal maxExit) {
        this.maxExit = maxExit;
        return this;
    }

    public BigDecimal getExitStep() {
        return exitStep;
    }

    public BestProfitData setExitStep(BigDecimal exitStep) {
        this.exitStep = exitStep;
        return this;
    }

    public BigDecimal getAmountInvested() {
        return amountInvested;
    }

    public BestProfitData setAmountInvested(BigDecimal amountInvested) {
        this.amountInvested = amountInvested;
        return this;
    }

    public int getMinNumberOfElements() {
        return minNumberOfElements;
    }

    public BestProfitData setMinNumberOfElements(int minNumberOfElements) {
        this.minNumberOfElements = minNumberOfElements;
        return this;
    }

    public int getMaxNumberOfElements() {
        return maxNumberOfElements;
    }

    public BestProfitData setMaxNumberOfElements(int maxNumberOfElements) {
        this.maxNumberOfElements = maxNumberOfElements;
        return this;
    }

    public int getNumberOfMonths() {
        return numberOfMonths;
    }

    public BestProfitData setNumberOfMonths(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
        return this;
    }

    public int getDelay() {
        return delay;
    }

    public BestProfitData setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    @Override
    public String toString() {
        return "BestProfitData{" +
                "minEntry=" + minEntry +
                ", maxEntry=" + maxEntry +
                ", entryStep=" + entryStep +
                ", minExit=" + minExit +
                ", maxExit=" + maxExit +
                ", exitStep=" + exitStep +
                ", amountInvested=" + amountInvested +
                ", minNumberOfElements=" + minNumberOfElements +
                ", maxNumberOfElements=" + maxNumberOfElements +
                ", numberOfMonths=" + numberOfMonths +
                ", delay=" + delay +
                '}';
    }
}
