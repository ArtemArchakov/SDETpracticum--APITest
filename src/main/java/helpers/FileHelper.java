package helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Этот класс предоставляет вспомогательные методы для работы с файлами,
 * позволяя считывать содержимое файлов в виде строки.
 */
public class FileHelper {

    /**
     * Метод считывает содержимое JSON-файла и возвращает его в виде строки.
     *
     * @param path путь к JSON-файлу, который нужно считать
     * @return содержимое файла в виде строки или null, если произошла ошибка
     */
    public static String readJsonFile(String path) {
        try {
            // Читаем все байты из файла по указанному пути и преобразуем их в строку
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            // В случае ошибки ввода-вывода выводим стек ошибки в консоль и возвращаем null
            e.printStackTrace();
            return null;
        }
    }
}