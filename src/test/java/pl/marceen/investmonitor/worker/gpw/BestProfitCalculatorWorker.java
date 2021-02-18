package pl.marceen.investmonitor.worker.gpw;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.analizer.control.ProfitCalculator;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.converter.boundary.JsonConverter;
import pl.marceen.investmonitor.gpw.control.RequestBuilder;
import pl.marceen.investmonitor.gpw.control.ResultGetter;
import pl.marceen.investmonitor.gpw.control.ResultMapper;
import pl.marceen.investmonitor.gpw.control.UrlBuilder;
import pl.marceen.investmonitor.gpw.entity.Instrument;
import pl.marceen.investmonitor.network.control.HttpClientProducer;
import pl.marceen.investmonitor.network.control.HttpExecutor;

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

    private ResultGetter resultGetter;
    private ProfitCalculator profitCalculator;
    private ArithmeticMovingAverage arithmeticMovingAverage;

    @Test
    void work() {
        OkHttpClient client = new HttpClientProducer().produce();
        resultGetter = new ResultGetter(
                new UrlBuilder(new RequestBuilder(), new JsonConverter()),
                new HttpExecutor<>(),
                new ResultMapper()
        );
        profitCalculator = new ProfitCalculator();
        arithmeticMovingAverage = new ArithmeticMovingAverage();

        Arrays.stream(Instrument.values())
                .filter(Instrument::isActive)
                .forEach(instrument -> calculate(client, instrument));
    }

    public void calculate(OkHttpClient client, Instrument instrument) {
        Result result = resultGetter.get(client, instrument, NUMBER_OF_MONTHS);

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
            parametersList.add(new Parameters(profitCalculator.calculate(arithmeticMovingAverage.calculate(result, i), AMOUNT, entry, exit, 1), i, entry, exit));
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
