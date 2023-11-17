package ru.clevertec.animal.util;

import org.yaml.snakeyaml.Yaml;
import ru.clevertec.Main;

import java.io.InputStream;
import java.util.Map;

public class LoadPropertyFromFile {

    private static final Map<String, Object> data;

    static {
        InputStream inputStream = Main.class.getClassLoader()
                .getResourceAsStream("application.yml");
        Yaml yaml = new Yaml();
        data = yaml.load(inputStream);
    }

    public static Integer getSizeCache() {
        return (Integer) data.get("sizeCache");
    }

    public static String getAlgorithm() {
        return (String) data.get("algorithm");
    }
}
