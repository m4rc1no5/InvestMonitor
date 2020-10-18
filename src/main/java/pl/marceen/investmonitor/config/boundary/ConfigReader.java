package pl.marceen.investmonitor.config.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * @author Marcin Zaremba
 */
public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    private static final String NEW_LINE = System.getProperty("line.separator");

    public static String read(Class clazz, String filename) throws Exception {
        logger.info("Try to read file {}", filename);

        StringBuilder stringBuilder = new StringBuilder();

        ClassLoader classLoader = clazz.getClassLoader();
        URL url = classLoader.getResource(filename);

        if (url == null) {
            throw new Exception("URL is null");
        }

        File file = new File(url.getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                stringBuilder.append(line).append(NEW_LINE);
            }
        } catch (IOException e) {
            throw new Exception(String.format("Read file problem: %s", e.getMessage()));
        }

        return stringBuilder.toString();
    }
}
