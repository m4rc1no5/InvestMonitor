package pl.marceen.investmonitor.gpw.boundary;

import pl.marceen.investmonitor.analizer.boundary.ArithmeticMovingAverageAnalyzer;
import pl.marceen.investmonitor.gpw.control.ResultGetter;
import pl.marceen.investmonitor.gpw.entity.Instrument;

/**
 * @author Marcin Zaremba
 */
public class GPWArithmeticMovingAverageAnalyzer extends ArithmeticMovingAverageAnalyzer<Instrument> {
    private static final String EMAIL_SUBJECT = "InvestMonitor - GPW";

    public GPWArithmeticMovingAverageAnalyzer() {
        resultGetter = new ResultGetter();
    }

    @Override
    protected String getEmailSubject() {
        return EMAIL_SUBJECT;
    }
}