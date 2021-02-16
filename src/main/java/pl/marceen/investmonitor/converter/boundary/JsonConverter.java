package pl.marceen.investmonitor.converter.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.bind.JsonbBuilder;
import java.util.Objects;

/**
 * @author Marcin Zaremba
 */
public class JsonConverter {
    private static final Logger logger = LoggerFactory.getLogger(JsonConverter.class);

    public String toJson(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }

        try (var jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(object);
        } catch (Exception e) {
            logger.info("Problem with building Jsonb - details: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
