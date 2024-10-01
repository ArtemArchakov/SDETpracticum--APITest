package helpers;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

/**
 * Этот класс служит для инициализации базовой спецификации запроса
 * с использованием библиотеки RestAssured.
 * Он позволяет определить общие настройки для всех HTTP-запросов,
 * такие как базовый URI, порт, тип содержимого и принимаемый тип.
 */
public class BaseRequests {

    /**
     * Метод инициализирует и возвращает спецификацию запроса,
     * которая используется в тестах API для унификации настроек.
     *
     * @return объект RequestSpecification с заданными настройками
     */
    public static io.restassured.specification.RequestSpecification initRequestSpecification() {
        return new RequestSpecBuilder()
                // Устанавливаем тип содержимого запроса как "application/json"
                .setContentType("application/json")
                // Устанавливаем базовый URI для запросов из RestAssured.baseURI
                .setBaseUri(RestAssured.baseURI)
                // Устанавливаем порт для запросов из RestAssured.port
                .setPort(RestAssured.port)
                // Указываем, что наш клиент будет принимать ответы в формате "application/json"
                .setAccept("application/json")
                // Строим и возвращаем объект спецификации запроса
                .build();
    }
}