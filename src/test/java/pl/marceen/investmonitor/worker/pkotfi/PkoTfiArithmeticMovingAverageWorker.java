package pl.marceen.investmonitor.worker.pkotfi;

import pl.marceen.investmonitor.pkotfi.boundary.ArithmeticMovingAverageCalculator;

/**
 * @author Marcin Zaremba
 */
public class PkoTfiArithmeticMovingAverageWorker {

    private static final boolean SEND_EMAIL = true;
    private static final int NUMBER_OF_MONTHS = 12;

    public static void main(String[] args) {
        new ArithmeticMovingAverageCalculator().calculate(NUMBER_OF_MONTHS, SEND_EMAIL);
    }
}
