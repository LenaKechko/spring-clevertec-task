package ru.clevertec.animal.exception;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Собственное исключение для обработки ситуации с невалидными данными
 *
 * @author Кечко Елена
 */

public class ValidatorException extends RuntimeException {

    /**
     * Сообщение должно быть именно такого формата
     *
     * @param field - имя проверяемого поля
     */
    public ValidatorException(Field field) {
        super("Validation failed for field " + field.getName() + ".");
    }

}
