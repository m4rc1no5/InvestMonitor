package pl.marceen.investmonitor.api.pkotfi.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.api.analizer.entity.Result;
import pl.marceen.investmonitor.api.pkotfi.control.PkoTfiSender;
import pl.marceen.investmonitor.api.pkotfi.control.ResultMapper;
import pl.marceen.investmonitor.api.pkotfi.entity.FundResponse;
import pl.marceen.investmonitor.api.pkotfi.entity.Subfund;

import javax.inject.Inject;

/**
 * @author Marcin Zaremba
 */
public class PkoTfiClient {
    private static final Logger logger = LoggerFactory.getLogger(PkoTfiClient.class);

    @Inject
    private PkoTfiSender pkoTfiSender;

    @Inject
    private ResultMapper resultMapper;

    public Result receiveData(Subfund subfund) {
        logger.info("Receiving data for {}", subfund);

        FundResponse fundResponse = pkoTfiSender.getFundResponse(subfund);
        return resultMapper.map(fundResponse.getDataList());
    }
}
