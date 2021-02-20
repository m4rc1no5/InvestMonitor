package pl.marceen.investmonitor.gpw.boundary;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.analizer.entity.Data;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.converter.boundary.JsonConverter;
import pl.marceen.investmonitor.email.boundary.EmailSender;
import pl.marceen.investmonitor.email.entity.EmailData;
import pl.marceen.investmonitor.gpw.control.RequestBuilder;
import pl.marceen.investmonitor.gpw.control.ResultGetter;
import pl.marceen.investmonitor.gpw.control.ResultMapper;
import pl.marceen.investmonitor.gpw.control.UrlBuilder;
import pl.marceen.investmonitor.gpw.entity.Instrument;
import pl.marceen.investmonitor.network.control.HttpClientProducer;
import pl.marceen.investmonitor.network.control.HttpExecutor;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Marcin Zaremba
 */
public class ArithmeticMovingAverageCalculator {
    private static final Logger logger = LoggerFactory.getLogger(ArithmeticMovingAverageCalculator.class);

    private static final String EMAIL_SUBJECT = "InvestMonitor - GPW";

    private final ResultGetter resultGetter;
    private final ArithmeticMovingAverage arithmeticMovingAverage;
    private final OkHttpClient httpClient;

    public ArithmeticMovingAverageCalculator() {
        this.resultGetter = new ResultGetter();
        this.arithmeticMovingAverage = new ArithmeticMovingAverage();
        this.httpClient = new HttpClientProducer().produce();
    }

    public void calculate(int numberOfMonths, boolean sendEmail) {
        String result = Arrays.stream(Instrument.values())
                .filter(Instrument::isActive)
                .map(instrument -> process(instrument, numberOfMonths))
                .collect(Collectors.joining("\n\n"));

        logger.info("Result: {}", result);

        if (!sendEmail) {
            return;
        }

        new EmailSender().send(
                new EmailData()
                        .subject(EMAIL_SUBJECT)
                        .text(result)
        );
    }

    private String process(Instrument instrument, int numberOfMonths) {
        Result result = resultGetter.get(httpClient, instrument, numberOfMonths);

        List<Data> dataList = arithmeticMovingAverage.calculate(result, instrument.getNumberOfElements());

        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add(String.format("Instrument: %s (%s)", instrument.name(), instrument.getFullName()));
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