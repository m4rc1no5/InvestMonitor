package pl.marceen.investmonitor.worker.pkotfi;

import pl.marceen.investmonitor.pkotfi.boundary.PkoTfiArithmeticMovingAverageAnalyzer;
import pl.marceen.investmonitor.pkotfi.entity.Subfund;

/**
 * @author Marcin Zaremba
 */
public class PkoTfiArithmeticMovingAverageWorker {

    private static final boolean SEND_EMAIL = false;
    private static final int NUMBER_OF_MONTHS = 12;

    public static void main(String[] args) {
        new PkoTfiArithmeticMovingAverageAnalyzer().calculate(Subfund.class, NUMBER_OF_MONTHS, SEND_EMAIL);
    }
}
