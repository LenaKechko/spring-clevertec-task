package ru.clevertec.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.servlet.AnimalServlet;

/**
 * Класс, отвечающий за дополнительную настройку приложения при запуске приложения.
 * Это достигается благодаря аннотации @WebListener
 */
@WebListener
@Slf4j
public class ServletListener implements ServletContextListener {

    /**
     * Метод отвечающий за инициализацию данных при запуске приложения
     * Здесь происходит внедрение раннее созданного сервлет в контекст spring-приложения
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
