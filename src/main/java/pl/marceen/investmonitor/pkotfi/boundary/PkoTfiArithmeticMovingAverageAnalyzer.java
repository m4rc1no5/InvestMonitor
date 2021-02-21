package pl.marceen.investmonitor.pkotfi.boundary;

import pl.marceen.investmonitor.analizer.boundary.ArithmeticMovingAverageAnalyzer;
import pl.marceen.investmonitor.pkotfi.control.ResultGetter;
import pl.marceen.investmonitor.pkotfi.entity.Subfund;

/**
 * @author Marcin Zaremba
 */
public class PkoTfiArithmeticMovingAverageAnalyzer extends ArithmeticMovingAverageAnalyzer<Subfund> {
    private static final String EMAIL_SUBJECT = "InvestMonitor - PKOTFI";

    public PkoTfiArithmeticMovingAverageAnalyzer() {
        resultGetter = new ResultGetter();
    }

    @Override
    protected String getEmailSubject() {
        return EMAIL_SUBJECT;
    }
}
