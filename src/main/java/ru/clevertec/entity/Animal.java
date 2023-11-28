package ru.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

/**
 * Класс реализующий сущность Animal, соответствует таблице БД animals
 * С помощью аннотация Lombok были созданы getter's и setter's для полей класса, toString
 * и возможность созадния объекта с помощью Builder
 * а также конструктор по умолчанию и конструктор со всеми полями
 *
 * @author Кечко Елена
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Animal {

    /**
     * Уникальный идентификатор. Генерируется базой. Является уникальным и не может быть null
     */
    private UUID id;
    /**
     * Наименование животного. До 15 символов, не может быть null.
     */
    private String name;
    /**
     * Вида животного. До 15 символов
     */
    private String typeOfAnimal;
    /**
     * Класс животного. До 15 символов
     */
    private String classOfAnimal;
    /**
     * Вес животного. Единица измерения кг. Не может быть отрицательным и нулевым
     */
    private double weight;
    /**
     * Высота животного. Единица измерения м. Не может быть отрицательным и нулевым
     */
    private double height;
    /**
     * Скорость животного. Единица измерения км/ч. Не может быть отрицательным и нулевым
     */
    private double speed;
}
