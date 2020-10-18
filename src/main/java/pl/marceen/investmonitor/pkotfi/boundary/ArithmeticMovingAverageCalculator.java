package pl.marceen.investmonitor.pkotfi.boundary;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.analizer.entity.Data;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.email.boundary.EmailSender;
import pl.marceen.investmonitor.email.entity.EmailData;
import pl.marceen.investmonitor.network.control.HttpClientProducer;
import pl.marceen.investmonitor.network.control.HttpExecutor;
import pl.marceen.investmonitor.pkotfi.control.RequestBuilder;
import pl.marceen.investmonitor.pkotfi.control.ResultGetter;
import pl.marceen.investmonitor.pkotfi.control.ResultMapper;
import pl.marceen.investmonitor.pkotfi.control.UrlBuilder;
import pl.marceen.investmonitor.pkotfi.entity.Subfund;

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

    private static final String EMAIL_SUBJECT = "InvestMonitor - arithmetic moving average report";

    private final ResultGetter resultGetter;
    private final ArithmeticMovingAverage arithmeticMovingAverage;
    private final OkHttpClient httpClient;

    public ArithmeticMovingAverageCalculator() {
        resultGetter = new ResultGetter(new UrlBuilder(), new RequestBuilder(), new HttpExecutor<>(), new ResultMapper());
        arithmeticMovingAverage = new ArithmeticMovingAverage();
        httpClient = new HttpClientProducer().produce();
    }

    public void calculate(int numberOfMonths, boolean sendEmail) {

        String result = Arrays.stream(Subfund.values())
                .filter(Subfund::isActive)
                .map(subfund -> calculate(httpClient, subfund, numberOfMonths))
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

    private String calculate(OkHttpClient client, Subfund subfund, int numberOfMonths) {
        Result result = resultGetter.get(client, subfund, numberOfMonths);
        List<Data> dataList = arithmeticMovingAverage.calculate(result, subfund.getNumberOfElements());

        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add(String.format("Subfund: %s (%s)", subfund.name(), subfund.getSubfundNameInPolish()));
        stringJoiner.add(String.format("Entry: %s", subfund.getEntry()));
        stringJoiner.add(String.format("Exit: %s", subfund.getExit()));

        dataList.stream()
                .skip(dataList.size() - subfund.getNumberOfElements())
                .forEach(data -> stringJoiner.add(getRow(data)));

        return stringJoiner.toString();
    }

    private String getRow(Data data) {
        return String.format("%s | %s | %s | %s", data.getDate(), data.getValue().setScale(2, RoundingMode.HALF_UP), data.getAverage(), data.getDeviation());
    }
}
