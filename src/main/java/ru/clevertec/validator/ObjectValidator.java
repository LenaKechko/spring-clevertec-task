package ru.clevertec.validator;

import ru.clevertec.exception.ValidatorException;
import ru.clevertec.validator.annotation.NumericFields;
import ru.clevertec.validator.annotation.TextFields;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * Класс для проверки валидации
 *
 * @author Кечко Елена
 */

public class ObjectValidator {

    /**
     * Метод выполняющий валидающию для каждого поля сущности
     *
     * @param object объект для валидации
     * @return true, если все поля успешно прошли валидацию
     * @throws ValidatorException     если поле не прошло валидающию
     * @throws IllegalAccessException если поле не известно
     */
    public static boolean validate(Object object) throws ValidatorException, IllegalAccessException {
        Class<?> objectClass = object.getClass();
        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(NumericFields.class)) {
                validateNumeric(field, object);
            }
            if (field.isAnnotationPresent(TextFields.class)) {
                validateText(field, object);
            }
        }
        return true;
    }

    /**
     * Метод выполняющий валидающию для числовых значений
     * над которыми стоит аннотация NumericField
     *
     * @param object объект для валидации
     * @param field  проверяемое поле
     * @throws ValidatorException     если поле не прошло валидающию
     * @throws IllegalAccessException если поле не известно
     */
    private static void validateNumeric(Field field, Object object) throws IllegalAccessException {
        NumericFields numericFieldsAnnotation = field.getAnnotation(NumericFields.class);
        double fieldValue = field.getDouble(object);

        double min = numericFieldsAnnotation.min();
        double max = numericFieldsAnnotation.max();

        if (fieldValue < min || fieldValue > max) {
            throw new ValidatorException(field);
        }
    }

    /**
     * Метод выполняющий валидающию для текстовых значений
     * над которыми стоит аннотация TextField
     *
     * @param object объект для валидации
     * @param field  проверяемое поле
     * @throws ValidatorException     если поле не прошло валидающию
     * @throws IllegalAccessException если поле не известно
     */

    private static void validateText(Field field, Object object) throws IllegalAccessException {
        TextFields textFieldsAnnotation = field.getAnnotation(TextFields.class);
        String fieldValue = (String) field.get(object);
        Pattern pattern = Pattern.compile(
                "^[а-яёА-ЯЁa-zA-Z \\-]{" + textFieldsAnnotation.minLength() + "," + textFieldsAnnotation.maxLength() + "}$",
                Pattern.UNICODE_CHARACTER_CLASS);
        if (!pattern.matcher(fieldValue).matches()) {
            throw new ValidatorException(field);
        }
    }
}
