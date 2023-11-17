package ru.clevertec.animal.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.clevertec.animal.validator.annotation.NumericFields;
import ru.clevertec.animal.validator.annotation.TextFields;

/**
 * {@link ru.clevertec.animal.entity.Animal}
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
     * {@link ru.clevertec.animal.entity.Animal}
     */
    @TextFields(minLength = 1)
    private String name;
    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    @TextFields
    private String typeOfAnimal;
    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    @TextFields
    private String classOfAnimal;
    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    @NumericFields
    private double weight;
    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    @NumericFields
    private double height;
    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    @NumericFields
    private double speed;
}
