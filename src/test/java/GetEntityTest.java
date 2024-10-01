import org.testng.annotations.Test;
import pojo.Entity;
import services.EntityService;

/**
 * Класс теста для проверки получения сущности по ID.
 * Наследуется от BaseApiTest, чтобы использовать функциональность базового класса,
 * которая включает создание и удаление сущностей до и после тестов.
 */
public class GetEntityTest extends BaseApiTest {

    /**
     * Тестовый метод для проверки получения сущности по ID с использованием метода getEntityById.
     * Убеждается, что сущность была успешно получена и имеет правильный ID.
     */
    @Test
    public void testGetEntityById() {
        // Получаем ID первой созданной сущности
        int entityId = previouslyCreatedEntityIds.get(0);

        // Получаем сущность по ее ID через сервисный метод
        Entity entity = EntityService.getEntityById(entityId);

        // Проверяем, что сущность не равна null и имеет ожидаемый ID
        assert entity != null : "Сущность не найдена с ID: " + entityId;
        assert entity.getId() == entityId : "ID сущности не соответствует ожидаемому значению.";
    }
}