import org.testng.annotations.Test;
import pojo.Entity;
import services.EntityService;
import java.util.List;

/**
 * Класс теста для проверки получения всех сущностей.
 * Наследуется от BaseApiTest, чтобы использовать функциональность базового класса,
 * которая включает создание и удаление сущностей до и после тестов.
 */
public class GetAllEntitiesTest extends BaseApiTest {

    /**
     * Тестовый метод для проверки корректности получения всех сущностей с помощью метода getAllEntities.
     * Убеждается, что все созданные сущности присутствуют в ответе.
     */
    @Test
    public void testGetAllEntities() {
        // Проверяем, что в списке созданных сущностей ровно три ID
        assert previouslyCreatedEntityIds.size() == 3 : "Метод setup не создал ровно три сущности.";

        // Получаем все сущности через сервисный метод
        List<Entity> allEntities = EntityService.getAllEntities();

        // Логируем ожидаемые и фактические ID сущностей для проверки
        System.out.println("Ожидаемые ID сущностей: " + previouslyCreatedEntityIds);
        System.out.println("Фактические ID сущностей из getAllEntities: " + allEntities.stream().map(Entity::getId).toList());

        // Проверяем, что количество возвращенных сущностей не меньше ожидаемого
        assert allEntities.size() >= previouslyCreatedEntityIds.size() : "Количество возвращенных сущностей (" + allEntities.size() + ") меньше ожидаемого (" + previouslyCreatedEntityIds.size() + ").";

        // Проверяем, что все созданные сущности присутствуют в ответе
        for (int entityId : previouslyCreatedEntityIds) {
            boolean entityFound = allEntities.stream().anyMatch(entity -> entity.getId().equals(entityId));
            assert entityFound : "Созданная сущность с ID " + entityId + " не была найдена в ответе getAll.";
        }
    }
}