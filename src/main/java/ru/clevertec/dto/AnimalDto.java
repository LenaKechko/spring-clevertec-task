package ru.clevertec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.clevertec.entity.Animal;
import ru.clevertec.validator.annotation.NumericFields;
import ru.clevertec.validator.annotation.TextFields;

/**
 * {@link Animal}
 *
 * @author Кечко Елена
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class AnimalDto {

    /**
     * {@link Animal}
     */
    @TextFields(minLength = 1)
    private String name;
    /**
     * {@link Animal}
     */
    @TextFields
    private String typeOfAnimal;
    /**
     * {@link Animal}
     */
    @TextFields
    private String classOfAnimal;
    /**
     * {@link Animal}
     */
    @NumericFields
    private double weight;
    /**
     * {@link Animal}
     */
    @NumericFields
    private double height;
    /**
     * {@link Animal}
     */
    @NumericFields
    private double speed;

    @Override
    public String toString() {
        return "Имя животного: " + name + "\n" +
                "Тип животного: " + typeOfAnimal + "\n" +
                "Класс животного: " + classOfAnimal + "\n" +
                "Вес: " + weight + "\n" +
                "Высота: " + height + "\n" +
                "Скорость: " + speed + "\n";
    }
}
