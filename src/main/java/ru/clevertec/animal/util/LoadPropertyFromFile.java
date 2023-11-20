package ru.clevertec.animal.util;

import org.yaml.snakeyaml.Yaml;
import ru.clevertec.Main;

import java.io.InputStream;
import java.util.Map;

/**
 * Класс для чтения данных из yaml-файла
 *
 * @author Кечко Елена
 */

public class LoadPropertyFromFile {

    /**
     * Поле для чтения данных из файла, организовата в статическом блоке
     */
    private static final Map<String, Object> data;

    static {
        InputStream inputStream = Main.class.getClassLoader()
                .getResourceAsStream("application.yml");
        Yaml yaml = new Yaml();
        data = yaml.load(inputStream);
    }

    /**
     * Метод возвращает размер кэша
     *
     * @return размер кэша
     */
    public static Integer getSizeCache() {
        return (Integer) data.get("sizeCache");
    }

    /**
     * Метод возвращает алгоритм кэширования
     *
     * @return название алгоритма
     */
    public static String getAlgorithm() {
        return (String) data.get("algorithm");
    }
}
