package ru.clevertec.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Класс для обработки данных приходящих от пользователя
 */
public class ReadInputUtil {

    /**
     * Метод отвечающий за парсинг пришедшей строки в формате json
     *
     * @param stream принимает поток
     */
    public static String readInputStream(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
    }
}
