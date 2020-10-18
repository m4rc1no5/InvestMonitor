package pl.marceen.investmonitor.worker;

import pl.marceen.investmonitor.pkotfi.boundary.ArithmeticMovingAverageCalculator;

/**
 * @author Marcin Zaremba
 */
public class PkoTfiArithmeticMovingAverageWorker {
    public static void main(String[] args) {
        new ArithmeticMovingAverageCalculator().calculate(12);
    }
}
