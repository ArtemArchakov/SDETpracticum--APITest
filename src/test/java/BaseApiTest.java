import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import services.EntityService;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс для API-тестов, который предоставляет общие методы для настройки и очистки.
 * Этот класс содержит логику создания и удаления сущностей до и после выполнения тестов.
 */
public class BaseApiTest {

    // Список для хранения ID созданных сущностей
    protected final List<Integer> previouslyCreatedEntityIds = new ArrayList<>();

    /**
     * Метод, который выполняется перед запуском всех тестов в классе (аналог setup).
     * Создает три сущности и сохраняет их ID в списке previouslyCreatedEntityIds.
     */
    @BeforeClass
    public void setup() {
        // Создаем три сущности и сохраняем их ID
        createEntity("first");
        createEntity("second");
        createEntity("third");

        // Логируем общее количество созданных сущностей
        System.out.println("Всего создано сущностей в setup: " + previouslyCreatedEntityIds.size());
    }

    /**
     * Вспомогательный метод для создания сущности с указанным порядком.
     *
     * @param entityOrder порядок создания сущности (например, "first", "second", "third")
     */
    private void createEntity(String entityOrder) {
        // Создание сущности через EntityService и сохранение ее ID
        int entityId = EntityService.createEntity();
        if (entityId > 0) {
            previouslyCreatedEntityIds.add(entityId);
            System.out.println("Создана " + entityOrder + " сущность с ID: " + entityId);
        } else {
            // Генерация исключения, если не удалось создать сущность
            throw new RuntimeException("Не удалось создать " + entityOrder + " сущность");
        }
    }

    /**
     * Метод, который выполняется после завершения всех тестов в классе (аналог cleanup).
     * Удаляет все созданные в ходе тестов сущности.
     */
    @AfterClass
    public void cleanup() {
        // Удаляем все созданные сущности из списка
        for (int entityId : previouslyCreatedEntityIds) {
            try {
                EntityService.deleteEntityById(entityId);
                System.out.println("Сущность с ID " + entityId + " успешно удалена после завершения тестов.");
            } catch (Exception e) {
                // Логируем ошибку, если не удалось удалить сущность
                System.err.println("Ошибка при удалении сущности с ID " + entityId + ": " + e.getMessage());
            }
        }
    }
}