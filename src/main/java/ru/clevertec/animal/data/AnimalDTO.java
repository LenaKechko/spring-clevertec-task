package ru.clevertec.animal.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

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
public class AnimalDTO {

    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    private String name;
    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    private String typeOfAnimal;
    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    private String classOfAnimal;
    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    private double weight;
    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    private double height;
    /**
     * {@link ru.clevertec.animal.entity.Animal}
     */
    private double speed;
}
