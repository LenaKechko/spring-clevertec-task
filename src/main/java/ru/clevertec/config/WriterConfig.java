package ru.clevertec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.clevertec.writer.IWriter;
import ru.clevertec.writer.impl.WriterPdfImpl;

/**
 * Конфигурационный класс для работы файлом-чеком
 */
@Configuration
@ComponentScan(basePackages = "ru.clevertec")
@PropertySource(value = "classpath:application.yml")
public class WriterConfig {

    /**
     * Зависимость для работы с данными из application.yml
     */
    @Autowired
    private BeanFactoryPostProcessor beanFactoryPostProcessor;
    /**
     * Путь, по которому сохраняется файл-чек
     */
    @Value("${fileCheck.path}")
    private String pathToFile;
    /**
     * Тип файла, который генерируется. Доступен только PDF
     */
    @Value("${fileCheck.type}")
    private String typeOfFile;

    /**
     * Бин для создания writer, который создает файл
     *
     * @return объект для создания файла конкретного типа
     */
    @Bean
    public IWriter<?> writer() {
        if (typeOfFile.equals("PDF")) {
            return new WriterPdfImpl<>(pathToFile);
        }
        throw new IllegalStateException("Unexpected value: " + typeOfFile);
    }
}