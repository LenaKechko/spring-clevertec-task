package ru.clevertec.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.servlet.AnimalServlet;

/**
 * Класс отвечающие за миграцию данных при запуске приложения
 * Для миграции использовалась Liquibase
 * Благодаря аннотации @WebListener миграция запускается при запуске приложения
 */

@WebListener
@Slf4j
public class ServletListener implements ServletContextListener {

    /**
     * Метод отвечающий за инициализацию данных при запуске приложения
     *
     * @param sce объект для работы с контекстом сервлета
     */
    public void contextInitialized(ServletContextEvent sce) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.clevertec");
        context.refresh();

        AnimalServlet animalServlet = context.getBean(AnimalServlet.class);
        ServletContext servletContext = sce.getServletContext();
        servletContext.addServlet("Animal", animalServlet)
                .addMapping("/animals/*");

    }

}
