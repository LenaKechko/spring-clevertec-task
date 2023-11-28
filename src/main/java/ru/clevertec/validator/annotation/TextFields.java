package ru.clevertec.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для проверки валидации текстовых полей сущности
 *
 * @author Кечко Елена
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TextFields {
    int minLength() default 0;

    int maxLength() default 15;
}
