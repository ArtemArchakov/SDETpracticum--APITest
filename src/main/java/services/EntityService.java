package services;

import pojo.Entity;
import helpers.BaseRequests;
import helpers.FileHelper;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.util.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Класс EntityService предоставляет методы для взаимодействия с API,
 * выполняя операции создания, получения, обновления и удаления сущностей.
 */
public class EntityService {

    // Список для хранения созданных сущностей
    public static final List<Entity> createdEntities = new ArrayList<>();

    private static final String BASE_PATH_GET_ALL = "/api/getAll";
    private static final String BASE_PATH_GET_BY_ID = "/api/get/{id}";
    private static final String BASE_PATH_DELETE = "/api/delete/{id}";
    private static final String BASE_PATH_PATCH = "/api/patch/{id}";

    static {
        // Регистрация кастомного парсера для обработки ответов в формате text/plain как JSON
        RestAssured.registerParser("text/plain", Parser.JSON);
    }

    /**
     * Метод для генерации динамических данных сущности на основе шаблона JSON.
     * Использует файл userCreationBody.json и заменяет плейсхолдеры на уникальные значения.
     *
     * @return строка JSON с данными сущности
     */
    public static String generateEntityData() {
        String template = FileHelper.readJsonFile("src/test/resources/json/userCreationBody.json");

        // Замена плейсхолдеров на динамические значения
        String uniqueTitle = "title-" + UUID.randomUUID();
        assert template != null;
        template = template.replace("Заголовок сущности", uniqueTitle)
                .replace("123", String.valueOf((int) (Math.random() * 1000)));

        return template;
    }

    /**
     * Метод для создания сущности и сохранения ее данных в список createdEntities.
     *
     * @return ID созданной сущности
     */
    public static int createEntity() {
        String requestBody = generateEntityData();

        Response response = (Response) given()
                .spec(BaseRequests.initRequestSpecification())
                .body(requestBody)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200) // Проверка, что статус ответа 200
                .extract() // Извлечение объекта Response
                .response();

        // Получение ID созданной сущности из тела ответа
        int createdEntityId = Integer.parseInt(response.getBody().asString().trim());

        // Проверка, что ID является числом
        response.then().body(equalTo(String.valueOf(createdEntityId)));

        // Получение созданной сущности и сохранение в списке
        Entity entity = getEntityById(createdEntityId);
        createdEntities.add(entity);

        return createdEntityId;
    }

    /**
     * Метод для получения всех сущностей, у которых verified = true.
     *
     * @return список сущностей
     */
    public static List<Entity> getAllEntities() {
        Response response = given()
                .spec(BaseRequests.initRequestSpecification())
                .queryParam("verified", true)
                .when()
                .get(BASE_PATH_GET_ALL)
                .then()
                .statusCode(200) // Проверка, что статус ответа 200
                // Проверка наличия обязательных полей в ответе
                .body("entity", notNullValue())
                .body("entity.addition", notNullValue())
                .body("entity.addition.additional_info", notNullValue())
                .body("entity.addition.additional_number", notNullValue())
                .body("entity.addition.id", notNullValue())
                .body("entity.id", notNullValue())
                .body("entity.important_numbers", notNullValue())
                .body("entity.important_numbers.size()", greaterThan(0))
                .body("entity.title", notNullValue())
                .body("entity.verified", everyItem(equalTo(true)))
                .extract()
                .response();

        System.out.println("Полный ответ из getAllEntities: " + response.asString());

        return response.jsonPath().getList("entity", Entity.class);
    }

    /**
     * Метод для получения сущности по ID.
     *
     * @param id ID сущности
     * @return объект сущности
     */
    public static Entity getEntityById(int id) {
        Response response = given()
                .spec(BaseRequests.initRequestSpecification())
                .pathParam("id", id)
                .when()
                .get(BASE_PATH_GET_BY_ID)
                .then()
                .log().body() // Логирование тела ответа
                .statusCode(200) // Проверка, что статус ответа 200
                // Проверка наличия обязательных полей
                .body("id", equalTo(id))
                .body("title", notNullValue())
                .body("verified", notNullValue())
                .body("addition", notNullValue())
                .body("addition.id", notNullValue())
                .body("addition.additional_info", notNullValue())
                .body("addition.additional_number", notNullValue())
                .body("important_numbers", notNullValue())
                .body("important_numbers.size()", greaterThan(0))
                .extract().response();

        return response.as(Entity.class);
    }

    /**
     * Метод для удаления сущности по ID с возможностью повторной попытки в случае неудачи.
     *
     * @param id ID сущности
     */
    public static void deleteEntityById(int id) {
        int retryCount = 3;
        while (retryCount > 0) {
            try {
                given()
                        .spec(BaseRequests.initRequestSpecification())
                        .pathParam("id", id)
                        .when()
                        .delete(BASE_PATH_DELETE)
                        .then()
                        .log().ifValidationFails() // Логирование при неудачной проверке
                        .statusCode(204).extract().response();

                System.out.println("Сущность с ID " + id + " успешно удалена.");
                return;
            } catch (AssertionError e) {
                retryCount--;
                System.err.println("Ошибка при удалении сущности с ID " + id + ": " + e.getMessage());
                if (retryCount == 0) {
                    throw e;
                }
                System.out.println("Повторная попытка удаления сущности с ID " + id + " (" + (3 - retryCount) + " из 3)");
            }
        }
    }

    /**
     * Метод для обновления (patch) сущности по ID.
     *
     * @param entityId ID сущности
     * @param updatedEntity объект с обновленными данными
     */
    public static void patchEntity(int entityId, Entity updatedEntity) {
        // Выполнение запроса PATCH
        Response response = given()
                .spec(BaseRequests.initRequestSpecification())
                .pathParam("id", entityId)
                .body(updatedEntity)
                .when()
                .patch(BASE_PATH_PATCH)
                .then()
                .statusCode(204) // Проверка, что статус ответа 204 (успешное обновление)
                .extract() // Извлечение объекта Response
                .response();

        // Выполнение запроса GET для подтверждения обновлений
        Entity retrievedEntity = getEntityById(entityId);

        // Проверка обновленных значений
        assert retrievedEntity.getAddition().getAdditional_info().equals(updatedEntity.getAddition().getAdditional_info());
        assert retrievedEntity.getAddition().getAdditional_number() == updatedEntity.getAddition().getAdditional_number();
        assert retrievedEntity.getImportant_numbers().equals(updatedEntity.getImportant_numbers());
        assert retrievedEntity.getTitle().equals(updatedEntity.getTitle());
        assert retrievedEntity.isVerified() == updatedEntity.isVerified();

        // Обновление сущности в списке после успешного обновления
        for (int i = 0; i < createdEntities.size(); i++) {
            if (createdEntities.get(i).getId() == entityId) {
                createdEntities.set(i, updatedEntity);
                break;
            }
        }
    }
}