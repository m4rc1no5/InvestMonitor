package pl.marceen.investmonitor.analizer.boundary;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.analizer.entity.Data;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.email.boundary.EmailSender;
import pl.marceen.investmonitor.email.entity.EmailData;
import pl.marceen.investmonitor.investment.entity.InstrumentInterface;
import pl.marceen.investmonitor.network.control.HttpClientProducer;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Marcin Zaremba
 */
public abstract class ArithmeticMovingAverageAnalyzer<T extends InstrumentInterface> {
    private static final Logger logger = LoggerFactory.getLogger(ArithmeticMovingAverageAnalyzer.class);

    protected final OkHttpClient httpClient;
    protected ArithmeticMovingAverage arithmeticMovingAverage;
    protected AbstractResultGetter resultGetter;

    public ArithmeticMovingAverageAnalyzer() {
        httpClient = new HttpClientProducer().produce();
        arithmeticMovingAverage = new ArithmeticMovingAverage();
    }

    public void calculate(Class<T> instrument, int numberOfMonths, boolean sendEmail) {
        String result = Arrays.stream(instrument.getEnumConstants())
                .filter(InstrumentInterface::isActive)
                .map(i -> process(i, numberOfMonths))
                .collect(Collectors.joining("\n\n"));

        logger.info("Result: {}", result);

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

        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add(String.format("Instrument: %s", instrument.getInstrumentName()));
        stringJoiner.add(String.format("Entry: %s", instrument.getEntry()));
        stringJoiner.add(String.format("Exit: %s", instrument.getExit()));

        dataList.stream()
                .skip(dataList.size() - instrument.getNumberOfElements())
                .forEach(data -> stringJoiner.add(getRow(data)));

        return stringJoiner.toString();
    }

    private String getRow(Data data) {
        return String.format("%s | %s | %s | %s", data.getDate(), data.getValue().setScale(2, RoundingMode.HALF_UP), data.getAverage(), data.getDeviation());
    }
}
