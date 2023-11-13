package ru.clevertec.animal.validator;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class ObjectValidator {

    public static boolean validate(Object object) throws IllegalAccessException {
        Class<?> objectClass = object.getClass();
        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(NumericFields.class)) {
                return validateNumeric(field, object);
            }
            if (field.isAnnotationPresent(TextFields.class)) {
                return validateText(field, object);
            }
        }
        return true;
    }

    private static boolean validateNumeric(Field field, Object object) throws IllegalAccessException {
        NumericFields numericFieldsAnnotation = field.getAnnotation(NumericFields.class);
        double fieldValue = field.getDouble(object);

        double min = numericFieldsAnnotation.min();
        double max = numericFieldsAnnotation.max();

        if (fieldValue < min || fieldValue > max) {
            System.out.println("Validation failed for field " + field.getName() + ".");
            return false;
        }
        return true;
    }

    private static boolean validateText(Field field, Object object) throws IllegalAccessException {
        TextFields textFieldsAnnotation = field.getAnnotation(TextFields.class);
        String fieldValue = (String) field.get(object);
        Pattern pattern = Pattern.compile(
                "^[а-яёА-ЯЁa-zA-Z \\-]{" + textFieldsAnnotation.minLength() + "," + textFieldsAnnotation.maxLength() + "}$",
                Pattern.UNICODE_CHARACTER_CLASS);
        boolean isValid = pattern.matcher(fieldValue).matches();
        if (!isValid) {
            System.out.println("Validation failed for field " + field.getName() + ".");
        }
        return isValid;
    }
}
