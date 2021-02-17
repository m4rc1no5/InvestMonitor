package pl.marceen.investmonitor.network.control;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.network.entity.NetworkException;

import javax.json.bind.JsonbBuilder;
import java.io.IOException;

/**
 * @author Marcin Zaremba
 */
public class HttpExecutor<T> {
    private static final Logger logger = LoggerFactory.getLogger(HttpExecutor.class);

    public T execute(Class<T> clazz, OkHttpClient client, Request request) throws NetworkException {
        logger.debug("START");

        Response response = getResponse(client, request);
        String responseAsString = getResponseAsString(response.body());
        logger.info("Raw response: {}", responseAsString);

        logger.debug("Try to convert response to object class {}", clazz.getSimpleName());
        T result = getResult(responseAsString, clazz);

        logger.debug("STOP");

        return result;
    }

    public String execute(OkHttpClient client, Request request) throws NetworkException {
        logger.info("START");

        Response response = getResponse(client, request);
        String responseAsString = getResponseAsString(response.body());
        logger.debug("Raw response: {}", responseAsString);

        return responseAsString;
    }

    private T getResult(String response, Class<T> clazz) throws NetworkException {
        if (isGPWResponse(response)) {
            logger.info("Found response from GPW - cut unnecessary characters");
            response = response.substring(2, response.length() - 1);
        }

        try (var jsonb = JsonbBuilder.create()) {
            return jsonb.fromJson(response, clazz);
        } catch (Exception e) {
            throw NetworkException.connectionProblem(e.getMessage(), logger);
        }
    }

    private boolean isGPWResponse(String response) {
        return response.startsWith("\t[") || response.startsWith("[") || response.startsWith("\t\n[") || response.startsWith("\n[");
    }

    private Response getResponse(OkHttpClient client, Request request) throws NetworkException {
        logger.debug("Try to get Response...");

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw NetworkException.connectionProblem(e.getMessage(), logger);
        }
    }

    private String getResponseAsString(ResponseBody responseBody) throws NetworkException {
        logger.debug("Try to convert response body to String...");

        if (responseBody == null) {
            throw NetworkException.noAnswer(logger);
        }

        try {
            return responseBody.string();
        } catch (Exception e) {
            throw NetworkException.connectionProblem(e.getMessage(), logger);
        }
    }
}
