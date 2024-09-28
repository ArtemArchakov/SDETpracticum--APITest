import org.testng.annotations.Test;
import pojo.Addition;
import pojo.Entity;
import services.EntityService;
import java.util.Arrays;

/**
 * Класс теста для проверки обновления (patch) сущности.
 * Наследуется от BaseApiTest, чтобы использовать функциональность базового класса,
 * которая включает создание и удаление сущностей до и после тестов.
 */
public class PatchEntityTest extends BaseApiTest {

    /**
     * Тестовый метод для обновления (patch) существующей сущности и проверки, что обновление прошло успешно.
     */
    @Test
    public void testPatchExistingEntity() {
        // Используем ID первой созданной сущности из списка
        int existingEntityId = previouslyCreatedEntityIds.get(0);

        // Определяем обновленные данные для существующей сущности
        Entity updatedEntity = Entity.builder()
                .title("Обновленный заголовок для ранее созданной сущности") // Устанавливаем новый заголовок
                .verified(false) // Изменяем статус верификации на false
                .addition(Addition.builder()
                        .additional_info("Обновленная дополнительная информация") // Устанавливаем обновленное дополнительное поле
                        .additional_number(999) // Устанавливаем новое числовое значение
                        .build())
                .important_numbers(Arrays.asList(55, 66, 77)) // Устанавливаем новый список важных чисел
                .build();

        // Вызываем метод patch для обновления существующей сущности
        EntityService.patchEntity(existingEntityId, updatedEntity);

        // Получаем сущность снова по ID, чтобы проверить, что обновление прошло успешно
        Entity retrievedEntity = EntityService.getEntityById(existingEntityId);

        // Проверяем, что полученная сущность соответствует обновленным данным
        assert retrievedEntity != null : "Сущность не была найдена после обновления.";
        assert "Обновленный заголовок для ранее созданной сущности".equals(retrievedEntity.getTitle()) : "Заголовок сущности не был обновлен корректно.";
        assert !retrievedEntity.isVerified() : "Статус верификации не был обновлен корректно.";
        assert "Обновленная дополнительная информация".equals(retrievedEntity.getAddition().getAdditional_info()) : "Поле additional_info не было обновлено корректно.";
        assert retrievedEntity.getAddition().getAdditional_number() == 999 : "Поле additional_number не было обновлено корректно.";
        assert retrievedEntity.getImportant_numbers().equals(Arrays.asList(55, 66, 77)) : "Поле important_numbers не было обновлено корректно.";
    }
}