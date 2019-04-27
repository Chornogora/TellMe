package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {

    public static Properties getProperties(String url) {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(url)) {
            property.load(fis);
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }

        return property;
    }
}
