package pl.marceen.investmonitor.analizer.boundary;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.control.ActionGetter;
import pl.marceen.investmonitor.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.analizer.control.BestPriceCalculator;
import pl.marceen.investmonitor.analizer.entity.Data;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.email.boundary.EmailSender;
import pl.marceen.investmonitor.email.entity.EmailData;
import pl.marceen.investmonitor.investment.entity.InstrumentInterface;
import pl.marceen.investmonitor.network.control.HttpClientProducer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Marcin Zaremba
 */
public abstract class ArithmeticMovingAverageAnalyzer<T extends InstrumentInterface> {
    private static final Logger logger = LoggerFactory.getLogger(ArithmeticMovingAverageAnalyzer.class);

    private final OkHttpClient httpClient;
    private final ArithmeticMovingAverage arithmeticMovingAverage;
    private final ActionGetter actionGetter;
    private final BestPriceCalculator bestPriceCalculator;
    protected AbstractResultGetter resultGetter;

    public ArithmeticMovingAverageAnalyzer() {
        httpClient = new HttpClientProducer().produce();
        arithmeticMovingAverage = new ArithmeticMovingAverage();
        actionGetter = new ActionGetter();
        bestPriceCalculator = new BestPriceCalculator();
    }

    public void calculate(Class<T> instrument, int numberOfMonths, boolean sendEmail) {
        String result = Arrays.stream(instrument.getEnumConstants())
                .filter(InstrumentInterface::isActive)
                .map(i -> process(i, numberOfMonths))
                .collect(Collectors.joining("\n\n"));

        logger.info("Result:\n{}", result);

        if (!sendEmail) {
            return;
        }

        new EmailSender().send(
                new EmailData()
                        .text(result)
                        .subject(getEmailSubject())
        );
    }

    protected abstract String getEmailSubject();

    private String process(InstrumentInterface instrument, int numberOfMonths) {
        Result result = resultGetter.get(httpClient, instrument, numberOfMonths);
        List<Data> dataList = arithmeticMovingAverage.calculate(result, instrument.getNumberOfElements());
        Collections.reverse(dataList);

        StringJoiner stringJoiner = new StringJoiner("\n");
        BigDecimal exit = instrument.getExit();
        BigDecimal entry = instrument.getEntry();
        stringJoiner.add(String.format("%s [%s - %s]", instrument.getInstrumentName(), exit, entry));
        Data lastResult = dataList.get(0);
        BigDecimal deviation = lastResult.getDeviation();
        BigDecimal average = lastResult.getAverage();
        stringJoiner.add(String.format("Actual deviation: %s [%s]", format(deviation), actionGetter.get(deviation, exit, entry)));
        stringJoiner.add(String.format("Best price for sell: %s PLN", format(bestPriceCalculator.calculate(average, exit))));
        stringJoiner.add(String.format("Best price for buy: %s PLN", format(bestPriceCalculator.calculate(average, entry))));

        stringJoiner.add("\nLast sessions:");
        dataList.stream()
                .limit(7)
                .forEach(data -> stringJoiner.add(getRow(data)));

        return stringJoiner.toString();
    }

    private String getRow(Data data) {
        return String.format("%s | %s PLN | %s PLN | %s", data.getDate(), format(data.getValue()), format(data.getAverage()), format(data.getDeviation()));
    }

    private BigDecimal format(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
