package pl.marceen.investmonitor.aws.boundary;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.gpw.boundary.GPWArithmeticMovingAverageAnalyzer;
import pl.marceen.investmonitor.gpw.entity.Instrument;
import pl.marceen.investmonitor.pkotfi.boundary.PkoTfiArithmeticMovingAverageAnalyzer;
import pl.marceen.investmonitor.pkotfi.entity.Subfund;
import software.amazon.awssdk.services.lambda.LambdaAsyncClient;
import software.amazon.awssdk.services.lambda.model.GetAccountSettingsRequest;
import software.amazon.awssdk.services.lambda.model.GetAccountSettingsResponse;

import java.util.concurrent.CompletableFuture;

/**
 * @author Marcin Zaremba
 */
public class Handler implements RequestHandler<SQSEvent, String> {

    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    private static final LambdaAsyncClient lambdaClient = LambdaAsyncClient.create();

    public Handler() {
        CompletableFuture<GetAccountSettingsResponse> accountSettings = lambdaClient.getAccountSettings(GetAccountSettingsRequest.builder().build());
        try {
            GetAccountSettingsResponse settings = accountSettings.get();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        logger.info("START");
        new PkoTfiArithmeticMovingAverageAnalyzer().calculate(Subfund.class, 12, true);
        new GPWArithmeticMovingAverageAnalyzer().calculate(Instrument.class, 12, true);
        logger.info("END");
        return null;
    }
}
