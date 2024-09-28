import org.testng.annotations.Test;
import services.EntityService;

/**
 * Класс теста для проверки удаления сущностей.
 * Наследуется от BaseApiTest, чтобы использовать функциональность базового класса,
 * которая включает создание и удаление сущностей до и после тестов.
 */
public class DeleteEntityTest extends BaseApiTest {

    /**
     * Тестовый метод для удаления сущности по ID и проверки успешности удаления.
     */
    @Test
    public void testDeleteEntityById() {
        // Удаляем третью созданную сущность (индекс 2 в списке)
        int entityId = previouslyCreatedEntityIds.remove(2); // Получаем и удаляем сущность из списка
        EntityService.deleteEntityById(entityId);
        System.out.println("Сущность с ID " + entityId + " удалена.");

        // Проверяем, что сущность действительно была удалена
        try {
            EntityService.getEntityById(entityId);
            // Если удалось получить сущность, это означает, что она не была удалена
            assert false : "Сущность с ID " + entityId + " не была удалена, как ожидалось.";
        } catch (AssertionError e) {
            // Проверяем сообщение об ошибке, чтобы убедиться, что запрос вернул статус 500 (не найдена)
            if (e.getMessage().contains("Expected status code <200> but was <500>")) {
                System.out.println("Сущность была успешно удалена, как и ожидалось. Записи не найдены.");
            } else {
                // Если ошибка другая, выбрасываем исключение дальше
                throw e;
            }
        }
    }
}