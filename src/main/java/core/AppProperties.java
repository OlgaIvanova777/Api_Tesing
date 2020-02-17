package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    public static final String FILE_PATH = "src/main/resources/app.properties";
    private static Properties properties = null;

    public static void initProperties() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream input = new FileInputStream(FILE_PATH)) {
                properties.load(input);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String getStringProperty(String propertyName) {
        initProperties();
        return properties.getProperty(propertyName);
    }
}
