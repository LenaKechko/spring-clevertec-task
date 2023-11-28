package ru.clevertec.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для проверки валидации числовых полей сущности
 *
 * @author Кечко Елена
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NumericFields {
    double min() default 0.0000000001;

    double max() default Double.POSITIVE_INFINITY;
}
