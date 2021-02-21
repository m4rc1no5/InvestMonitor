package pl.marceen.investmonitor.worker.gpw;


import pl.marceen.investmonitor.gpw.boundary.GPWArithmeticMovingAverageAnalyzer;
import pl.marceen.investmonitor.gpw.entity.Instrument;

/**
 * @author Marcin Zaremba
 */
public class GpwArithmeticMovingAverageWorker {

    private static final boolean SEND_EMAIL = false;
    private static final int NUMBER_OF_MONTHS = 24;

    public static void main(String[] args) {
        new GPWArithmeticMovingAverageAnalyzer().calculate(Instrument.class, NUMBER_OF_MONTHS, SEND_EMAIL);
    }
}
