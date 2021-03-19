package pl.marceen.investmonitor.analizer.boundary;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.analizer.control.ArithmeticMovingAverage;
import pl.marceen.investmonitor.analizer.entity.BestProfitData;
import pl.marceen.investmonitor.analizer.entity.BestProfitResult;
import pl.marceen.investmonitor.analizer.entity.Result;
import pl.marceen.investmonitor.investment.entity.InstrumentInterface;
import pl.marceen.investmonitor.network.control.HttpClientProducer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class BestProfitCalculator {
    private static final Logger logger = LoggerFactory.getLogger(BestProfitCalculator.class);

    private final OkHttpClient client;
    private final AbstractResultGetter resultGetter;
    private final BestProfitData data;
    private final ProfitCalculator profitCalculator;
    private final ArithmeticMovingAverage arithmeticMovingAverage;

    public BestProfitCalculator(AbstractResultGetter resultGetter, BestProfitData data) {
        this.resultGetter = resultGetter;
        this.data = data;

        client = new HttpClientProducer().produce();
        profitCalculator = new ProfitCalculator();
        arithmeticMovingAverage = new ArithmeticMovingAverage();
    }

    public BestProfitResult calculate(InstrumentInterface instrument) {
        Result result = resultGetter.get(client, instrument, data.getNumberOfMonths());

        BigDecimal entry = data.getMinEntry();
        BigDecimal exit;
        List<BestProfitResult> parametersList = new ArrayList<>();

        do {
            exit = data.getMinExit();
            do {
                scan(parametersList, result, entry, exit);
                exit = exit.add(data.getExitStep());
            } while (exit.compareTo(data.getMaxExit()) > 0);

            scan(parametersList, result, entry, exit);
            entry = entry.add(data.getEntryStep());
        } while (entry.compareTo(data.getMaxEntry()) < 0);

        BestProfitResult max = Collections.max(parametersList, Comparator.comparing(BestProfitResult::getProfit));
        logger.info("Result: {}", max);

        return max;
    }

    private void scan(List<BestProfitResult> parametersList, Result result, BigDecimal entry, BigDecimal exit) {
        for (int i = data.getMinNumberOfElements(); i <= data.getMaxNumberOfElements(); i++) {
            parametersList.add(new BestProfitResult(profitCalculator.calculate(arithmeticMovingAverage.calculate(result, i), data.getAmountInvested(), entry, exit, data.getDelay()), i, entry, exit));
        }
    }
}
