package pl.marceen.investmonitor.email.entity;

import org.slf4j.Logger;

/**
 * @author Marcin Zaremba
 */
public class EmailException extends RuntimeException {
    private EmailException(String message) {
        super(message);
    }

    public static EmailException sendingProblem(String message, Logger logger) {
        logger.error(message);

        return new EmailException(message);
    }
}
