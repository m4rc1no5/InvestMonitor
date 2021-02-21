package pl.marceen.investmonitor.worker.pkotfi;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.analizer.boundary.ProfitCalculator;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.network.control.HttpClientProducer;
import pl.marceen.investmonitor.pkotfi.control.ResultGetter;
import pl.marceen.investmonitor.pkotfi.entity.Subfund;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class BestProfitCalculatorWorker {
    private static final Logger logger = LoggerFactory.getLogger(BestProfitCalculatorWorker.class);

    private static final BigDecimal MIN_ENTRY = new BigDecimal("0.5");
    private static final BigDecimal MAX_ENTRY = new BigDecimal("1.5");
    private static final BigDecimal ENTRY_STEP = new BigDecimal("0.1");

    private static final BigDecimal MIN_EXIT = new BigDecimal("-0.5");
    private static final BigDecimal MAX_EXIT = new BigDecimal("-1.5");
    private static final BigDecimal EXIT_STEP = new BigDecimal("-0.1");

    private static final BigDecimal AMOUNT = new BigDecimal(10000);

    private static final int MIN_NUMBER_OF_ELEMENTS = 20;
    private static final int MAX_NUMBER_OF_ELEMENTS = 100;
    private static final int NUMBER_OF_MONTHS = 60;

    private ResultGetter resultGetter;
    private ProfitCalculator profitCalculator;
    private ArithmeticMovingAverage arithmeticMovingAverage;

    @Test
    void work() {
        OkHttpClient client = new HttpClientProducer().produce();
        resultGetter = new ResultGetter();
        profitCalculator = new ProfitCalculator();
        arithmeticMovingAverage = new ArithmeticMovingAverage();

        Arrays.stream(Subfund.values())
                .filter(Subfund::isActive)
                .forEach(subfund -> calculate(client, subfund));
    }

    public void calculate(OkHttpClient client, Subfund subfund) {
        Result result = resultGetter.get(client, subfund, NUMBER_OF_MONTHS);

        BigDecimal entry = MIN_ENTRY;
        BigDecimal exit;
        List<Parameters> parametersList = new ArrayList<>();

        do {
            exit = MIN_EXIT;
            do {
                scan(parametersList, result, entry, exit);
                exit = exit.add(EXIT_STEP);
            } while (exit.compareTo(MAX_EXIT) > 0);

            scan(parametersList, result, entry, exit);
            entry = entry.add(ENTRY_STEP);
        } while (entry.compareTo(MAX_ENTRY) < 0);

        Parameters max = Collections.max(parametersList, Comparator.comparing(Parameters::getProfit));
        logger.info("Result: {}", max);
    }

    private void scan(List<Parameters> parametersList, Result result, BigDecimal entry, BigDecimal exit) {
        for (int i = MIN_NUMBER_OF_ELEMENTS; i <= MAX_NUMBER_OF_ELEMENTS; i++) {
            parametersList.add(new Parameters(profitCalculator.calculate(arithmeticMovingAverage.calculate(result, i), AMOUNT, entry, exit, 3), i, entry, exit));
        }
    }

    private static class Parameters {
        private final BigDecimal profit;
        private final int numberOfElements;
        private final BigDecimal entry;
        private final BigDecimal exit;

        public Parameters(BigDecimal profit, int numberOfElements, BigDecimal entry, BigDecimal exit) {
            this.profit = profit;
            this.numberOfElements = numberOfElements;
            this.entry = entry;
            this.exit = exit;
        }

        public BigDecimal getProfit() {
            return profit;
        }

        @Override
        public String toString() {
            return "Parameters{" +
                    "profit=" + profit +
                    ", numberOfElements=" + numberOfElements +
                    ", entry=" + entry +
                    ", exit=" + exit +
                    '}';
        }
    }
}
