package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс PropertyProvider предназначен для загрузки и предоставления доступа
 * к конфигурационным свойствам из XML-файла (env-default.xml).
 * Реализует паттерн "Singleton" для обеспечения единственного экземпляра класса.
 */
public class PropertyProvider {

    // Единственный экземпляр класса (Singleton)
    private static PropertyProvider instance;

    // Объект Properties для хранения загруженных свойств
    private final Properties properties;

    /**
     * Приватный конструктор загружает свойства из файла env-default.xml.
     * Используется для создания единственного экземпляра класса.
     */
    private PropertyProvider() {
        properties = new Properties();

        // Загрузка свойств из файла env-default.xml
        try (InputStream xmlStream = getClass().getClassLoader().getResourceAsStream("env-default.xml")) {
            if (xmlStream != null) {
                properties.loadFromXML(xmlStream);
                System.out.println("env-default.xml успешно загружен.");
            } else {
                // Сообщение об ошибке, если файл не найден
                System.err.println("env-default.xml не найден в classpath");
            }
        } catch (IOException e) {
            // Обработка исключения в случае ошибки загрузки файла
            e.printStackTrace();
            throw new RuntimeException("Невозможно загрузить XML конфигурационный файл.");
        }
    }
}