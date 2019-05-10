package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Property {
    private static Property instance = null;
    private static Logger logger = Logger.getLogger(Property.class.getName());
    private Properties property;

    private Property() {
        property = new Properties();
    }

    public static Property getInstance() {
        if (instance == null)
            instance = new Property();
        return instance;
    }


    public Properties getProperties(String url) {

        try (FileInputStream fis = new FileInputStream(url)) {
            property.load(fis);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "ОШИБКА: Файл свойств отсуствует!", e);
        }

        return property;
    }
}
