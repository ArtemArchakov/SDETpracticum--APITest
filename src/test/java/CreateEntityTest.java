import org.testng.annotations.Test;

/**
 * Класс теста для проверки создания сущностей.
 * Наследуется от BaseApiTest, чтобы использовать функциональность базового класса,
 * которая включает создание и удаление сущностей до и после тестов.
 */
public class CreateEntityTest extends BaseApiTest {

    /**
     * Тестовый метод, который проверяет, что были созданы три сущности.
     * Проверяет, что список previouslyCreatedEntityIds содержит не менее трех ID.
     */
    @Test
    public void testCreateEntity() {
        // Проверяем, что сущности были созданы и список содержит не менее 3 ID
        assert previouslyCreatedEntityIds.size() == 3 : "Не удалось создать три сущности. Список должен содержать не менее 3 ID.";
    }
}