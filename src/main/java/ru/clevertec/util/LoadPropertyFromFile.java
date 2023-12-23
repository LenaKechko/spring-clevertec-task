package ru.clevertec.util;

import org.yaml.snakeyaml.Yaml;

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
        InputStream inputStream = LoadPropertyFromFile.class.getClassLoader()
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

    /**
     * Метод возвращает property подключения liquibase
     *
     * @return путь к changelog для миграции БД
     */
    public static String getLiquibaseChangelog() {
        return (String) data.get("liquibase.change-log");
    }

    /**
     * Метод возвращает property подключения liquibase
     *
     * @return true/false для миграции данных в БД или нет
     */
    public static Boolean getLiquibaseEnable() {
        return (Boolean) data.get("liquibase.enable");
    }

    /**
     * Метод возвращает путь, по которому сохраняется чек с животным
     *
     * @return путь
     */
    public static String getPath() {
        return (String) data.get("pathForCheck");
    }
}
