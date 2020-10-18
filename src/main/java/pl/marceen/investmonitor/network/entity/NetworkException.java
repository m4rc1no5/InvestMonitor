package pl.marceen.investmonitor.network.entity;

import org.slf4j.Logger;

/**
 * @author Marcin Zaremba
 */
public class NetworkException extends RuntimeException {

    private static final String MSG_CONNECTION_PROBLEM = "Connection problem";
    private static final String MSG_NO_ANSWER = "No answer";
    private static final String RESPONSE_IS_EMPTY = "Response is empty";

    public static NetworkException connectionProblem(String details, Logger logger) {
        logger.error("{} - details: {}", MSG_CONNECTION_PROBLEM, details);

        return new NetworkException(MSG_CONNECTION_PROBLEM);
    }

    public static NetworkException noAnswer(Logger logger) {
        logger.error(RESPONSE_IS_EMPTY);

        return new NetworkException(MSG_NO_ANSWER);
    }

    private NetworkException(String message) {
        super(message);
    }
}
