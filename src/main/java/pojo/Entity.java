package pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Этот класс является POJO (Plain Old Java Object), который представляет сущность "Entity".
 * Используется для хранения и передачи данных в формате объекта.
 *
 * Аннотации Lombok позволяют автоматически генерировать необходимые методы,
 * а также конструкторы и реализацию шаблона "Builder".
 *
 * Аннотация @JsonIgnoreProperties позволяет игнорировать неизвестные свойства при десериализации объекта из JSON.
 */
@Data // Автоматически генерирует геттеры, сеттеры, а также методы toString(), equals() и hashCode()
@Builder // Предоставляет шаблон проектирования "Builder" для удобного создания объектов
@NoArgsConstructor // Создает конструктор без аргументов
@AllArgsConstructor // Создает конструктор с аргументами для всех полей класса
@JsonIgnoreProperties // Позволяет игнорировать неизвестные свойства JSON при десериализации
public class Entity {

    private Integer id;

    // Поле типа Addition, представляющее дополнительную информацию
    private Addition addition;

    // Список важных чисел, связанных с этой сущностью
    private List<Integer> important_numbers;

    // Название или заголовок этой сущности
    private String title;

    // Флаг, указывающий, является ли сущность проверенной
    private boolean verified;
}