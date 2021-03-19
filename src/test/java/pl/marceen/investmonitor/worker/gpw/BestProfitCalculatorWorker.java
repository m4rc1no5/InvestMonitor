package pl.marceen.investmonitor.worker.gpw;

import org.junit.jupiter.api.Test;
import pl.marceen.investmonitor.analizer.boundary.BestProfitCalculator;
import pl.marceen.investmonitor.analizer.entity.BestProfitData;
import pl.marceen.investmonitor.gpw.control.ResultGetter;
import pl.marceen.investmonitor.gpw.entity.Instrument;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author Marcin Zaremba
 */
public class BestProfitCalculatorWorker {
    private static final BigDecimal MIN_ENTRY = new BigDecimal("1.0");
    private static final BigDecimal MAX_ENTRY = new BigDecimal("3.0");
    private static final BigDecimal ENTRY_STEP = new BigDecimal("0.2");

    private static final BigDecimal MIN_EXIT = new BigDecimal("-2.0");
    private static final BigDecimal MAX_EXIT = new BigDecimal("-5.0");
    private static final BigDecimal EXIT_STEP = new BigDecimal("-0.2");

    private static final BigDecimal AMOUNT = new BigDecimal(10000);

    private static final int MIN_NUMBER_OF_ELEMENTS = 20;
    private static final int MAX_NUMBER_OF_ELEMENTS = 100;
    private static final int NUMBER_OF_MONTHS = 60;
    private static final int DELAY = 1;

    @Test
    void work() {
        BestProfitCalculator bestProfitCalculator = new BestProfitCalculator(
                new ResultGetter(),
                new BestProfitData()
                        .setMinEntry(MIN_ENTRY)
                        .setMaxEntry(MAX_ENTRY)
                        .setEntryStep(ENTRY_STEP)
                        .setMinExit(MIN_EXIT)
                        .setMaxExit(MAX_EXIT)
                        .setExitStep(EXIT_STEP)
                        .setAmountInvested(AMOUNT)
                        .setMinNumberOfElements(MIN_NUMBER_OF_ELEMENTS)
                        .setMaxNumberOfElements(MAX_NUMBER_OF_ELEMENTS)
                        .setNumberOfMonths(NUMBER_OF_MONTHS)
                        .setDelay(DELAY)
        );

        Arrays.stream(Instrument.values())
                .filter(Instrument::isActive)
                .forEach(bestProfitCalculator::calculate);
    }
}
