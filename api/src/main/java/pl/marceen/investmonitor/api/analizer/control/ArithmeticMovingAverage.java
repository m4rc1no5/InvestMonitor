package pl.marceen.investmonitor.api.analizer.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.api.analizer.entity.Data;
import pl.marceen.investmonitor.api.analizer.entity.Result;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class ArithmeticMovingAverage {
    private static final Logger logger = LoggerFactory.getLogger(ArithmeticMovingAverage.class);

    // private static final int NUMBER_OF_ELEMENTS = 23;
    // protected static final BigDecimal ENTER = new BigDecimal("0.6");
    // protected static final BigDecimal ESCAPE = new BigDecimal("-0.8");

    public void calculate(Result result, int numberOfElements, BigDecimal enter, BigDecimal escape) {
        List<Data> dataList = result.getDataList();
        int size = dataList.size();

        List<Data> resultList = new ArrayList<>();

        BigDecimal amount = new BigDecimal(10000);
        BigDecimal value = dataList.get(0).getValue();
        BigDecimal points = amount.divide(value, 3, RoundingMode.CEILING);
        boolean in = true;

        for (int i = 0; i < size; i++) {
            if (i == size - numberOfElements) {
                break;
            }

            Data element = dataList.stream()
                    .skip(numberOfElements + i)
                    .findFirst()
                    .orElseThrow();

            BigDecimal avg = dataList.stream()
                    .skip(i)
                    .limit(numberOfElements)
                    .map(Data::getValue)
                    .reduce(BigDecimal::add)
                    .orElseThrow()
                    .divide(new BigDecimal(numberOfElements), 4, RoundingMode.CEILING);

            element.setAverage(avg);

            BigDecimal deviation = element.getValue().subtract(avg)
                    .divide(avg, 6, RoundingMode.CEILING)
                    .multiply(new BigDecimal(100));

            element.setDeviation(deviation);

            resultList.add(element);

            if (element.getDeviation().compareTo(escape) < 0 && in) {
                // logger.info("===");
                // logger.info("Escaping on {} - deviation: {}", element.getDate(), deviation);
                amount = element.getValue().multiply(points);
                // logger.info("Amount: {}", amount);
                // logger.info("Points: {}", points);
                in = false;
            }

            if (element.getDeviation().compareTo(enter) > 0 && !in) {
                // logger.info("===");
                // logger.info("Entering on {} - deviation: {}", element.getDate(), deviation);
                points = amount.divide(element.getValue(), 3, RoundingMode.CEILING);
                // logger.info("Amount: {}", amount);
                // logger.info("Points: {}", points);
                in = true;
            }
        }

        // resultList.forEach(this::log);

        BigDecimal totalAmount = result.getDataList().get(size-1).getValue().multiply(points);
        logger.info("Total amount: {} ({}, {}, {})", totalAmount, numberOfElements, enter, escape);
    }

    private void log(Data data) {
        logger.info("{} | {} | {} | {}", data.getDate(), data.getValue().setScale(2, RoundingMode.HALF_UP), data.getAverage(), data.getDeviation());
    }
}
