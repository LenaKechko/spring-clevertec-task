package ru.clevertec.exception;

import java.util.UUID;

/**
 * Собственное исключение для обработки ситуации с несуществующим Animal
 *
 * @author Кечко Елена
 */

public class AnimalNotFoundException extends RuntimeException {

    /**
     * Сообщение должно быть именно такого формата
     *
     * @param uuid - идентификатор продукта
     */
    public AnimalNotFoundException(UUID uuid) {
        super(String.format("Animal with uuid: %s not found", uuid));
    }
}
