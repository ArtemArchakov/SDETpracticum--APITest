package pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Этот класс является POJO (Plain Old Java Object), который представляет сущность "Addition".
 * Используется для хранения и передачи данных в формате объекта.
 *
 * Аннотации Lombok позволяют автоматически генерировать необходимый код,
 * такой как геттеры, сеттеры, конструкторы и методы toString(), equals() и hashCode().
 */
@Data // Автоматически генерирует геттеры, сеттеры, а также методы toString(), equals() и hashCode()
@Builder // Предоставляет шаблон проектирования "Builder" для удобного создания объектов
@NoArgsConstructor // Создает конструктор без аргументов
@AllArgsConstructor // Создает конструктор с аргументами для всех полей класса
public class Addition {

    // Уникальный идентификатор для объекта
    private Integer id;

    // Дополнительная информация в виде строки
    private String additional_info;

    // Дополнительное числовое значение
    private int additional_number;
}