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

@Configuration
@ComponentScan(basePackages = "ru.clevertec")
@PropertySource(value = "classpath:application.yml")
public class WriterConfig {

    @Autowired
    private BeanFactoryPostProcessor beanFactoryPostProcessor;
    @Value("${fileCheck.path}")
    private String pathToFile;
    @Value("${fileCheck.type}")
    private String typeOfFile;

    @Bean
    public IWriter<?> writer() {
        if (typeOfFile.equals("PDF")) {
            return new WriterPdfImpl<>(pathToFile);
        }
        throw new IllegalStateException("Unexpected value: " + typeOfFile);
    }
}