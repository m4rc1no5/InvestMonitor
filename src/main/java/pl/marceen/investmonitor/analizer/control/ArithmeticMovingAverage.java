package pl.marceen.investmonitor.analizer.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.entity.Data;
import pl.marceen.investmonitor.analizer.entity.Result;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class ArithmeticMovingAverage {
    private static final Logger logger = LoggerFactory.getLogger(ArithmeticMovingAverage.class);

    public List<Data> calculate(Result result, int numberOfElements) {
        List<Data> dataList = result.getDataList();
        int size = dataList.size();
        logger.debug("Data size: {}", size);

        List<Data> resultList = new ArrayList<>();
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
        }

        return resultList;
    }
}
