package ru.clevertec.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.config.ConfigService;
import ru.clevertec.dto.AnimalDto;
import ru.clevertec.exception.AnimalNotFoundException;
import ru.clevertec.exception.ValidatorException;
import ru.clevertec.util.ReadInputUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

/**
 * Класс (сервлет), отвечающий за обработку запросов о животных
 * Обрабатывает запросы приходящие по пути '/animals/*'
 */

@WebServlet(name = "Animal", urlPatterns = "/animals/*")
@Slf4j
public class AnimalServlet extends HttpServlet {


    /**
     * Метод обрабатывающий запросы GET
     * /animals - выдаст первых 20 животных
     * /animals?page=2 - выдаст следующие 20 животных
     * /animals?uuid={uuid} или /animals/{uuid} - выдаст конкретное животное по его uuid
     * В случае успешного выполнения запроса будет присвоен статус 200,
     * в случае возникшего исключения (отсутствия животного с таким uuid) - статус 404
     *
     * @param req  запрос от пользователя
     * @param resp ответ сервера
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = parseUUID(req);
        String json;
        ObjectMapper objectMapper = new ObjectMapper();
        if (uuid == null) {
            String pageParameter = req.getParameter("page");
            int page = 1;
            if (pageParameter != null)
                page = Integer.parseInt(pageParameter);
            List<AnimalDto> animals = ConfigService.getService().getAll(page, 20);
            json = objectMapper.writeValueAsString(animals);
            successfulProcess(resp, json);
        } else {
            try {
                AnimalDto animal = ConfigService.getService().get(UUID.fromString(uuid));
                json = objectMapper.writeValueAsString(animal);
                successfulProcess(resp, json);
            } catch (AnimalNotFoundException e) {
                failProcess(resp, e.getMessage());
            }
        }
    }

    /**
     * Метод обрабатывающий запросы POST
     * /animals - проверяет пришедший json и создает по данным животное
     * В случае успешного выполнения запроса будет присвоен статус 200 и выведется uuid созданного животного,
     * в случае возникшего исключения (отсутствия животного с таким uuid) - статус 404
     *
     * @param req  запрос от пользователя
     * @param resp ответ сервера
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AnimalDto animalDto = parseRequest(req);
        try {
            UUID uuid = ConfigService.getService().create(animalDto);
            successfulProcess(resp, String.valueOf(uuid));
        } catch (ValidatorException | AnimalNotFoundException e) {
            failProcess(resp, e.getMessage());
        }
    }

    /**
     * Метод обрабатывающий запросы PUT
     * /animals/{uuid} - проверяет пришедший json и по введенному uuid обновляет данные о животном
     * /animals?uuid={uuid}&name={name}&... - по параметрам обновляет данные о животном
     * В случае успешного выполнения запроса будет присвоен статус 200,
     * в случае возникшего исключения (отсутствия животного с таким uuid) - статус 404
     *
     * @param req  запрос от пользователя
     * @param resp ответ сервера
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = parseUUID(req);
        AnimalDto animalDto = parseRequest(req);
        try {
            ConfigService.getService().update(UUID.fromString(uuid), animalDto);
            successfulProcess(resp, "Success update entity");
        } catch (ValidatorException e) {
            failProcess(resp, e.getMessage());
        }
    }

    /**
     * Метод обрабатывающий запросы DELETE
     * /animals/{uuid} или /animals?uuid={uuid} - по uuid удаляется данные о животном
     * В случае успешного выполнения запроса будет присвоен статус 200,
     *
     * @param req  запрос от пользователя
     * @param resp ответ сервера
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String uuid = parseUUID(req);

        ConfigService.getService().delete(UUID.fromString(uuid));

        resp.setStatus(200);
    }

    /**
     * Успешное выполнение запроса и вывод результатов
     * Выставление статуса 200 серверу
     *
     * @param resp ответ сервера
     * @param obj  сообщение пользователю
     */
    private void successfulProcess(HttpServletResponse resp, String obj) throws IOException {
        try (PrintWriter out = resp.getWriter()) {
            out.write(obj);
            resp.setStatus(200);
            log.info("Process successful");
        }
    }

    /**
     * Выполнение запроса с ошибкой
     * Выставление статуса 404 серверу
     *
     * @param resp ответ сервера
     * @param obj  сообщение пользователю (сообщение из исключения)
     */
    private void failProcess(HttpServletResponse resp, String obj) {
        resp.setStatus(404);
        log.info("Process fail. " + obj);
    }

    /**
     * Метод для обработки приходящих параметров
     *
     * @param req запрос пользователя
     */
    private AnimalDto parseRequest(HttpServletRequest req) throws IOException {
        if (!req.getParameterMap().isEmpty()) {
            String temp;
            String name = req.getParameter("name");
            String typeOfAnimal = req.getParameter("typeOfAnimal");
            String classOfAnimal = req.getParameter("classOfAnimal");
            double weight = (temp = req.getParameter("weight")) == null ? 0.0 : Double.parseDouble(temp);
            double height = (temp = req.getParameter("height")) == null ? 0.0 : Double.parseDouble(temp);
            double speed = (temp = req.getParameter("speed")) == null ? 0.0 : Double.parseDouble(temp);
            return new AnimalDto(name, typeOfAnimal, classOfAnimal, weight, height, speed);
        } else {
            String json = ReadInputUtil.readInputStream(req.getInputStream());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, AnimalDto.class);
        }
    }

    /**
     * Метод для обработки приходящего uuid, если есть. Иначе возвращается null
     *
     * @param req запрос пользователя
     */
    private String parseUUID(HttpServletRequest req) {
        String uuid = req.getParameter("uuid");
        String uri = req.getRequestURI();
        if (uuid == null && uri.contains("/animals/")) {
            uuid = uri.substring(uri.indexOf("/animals/") + "/animals/".length());
        }
        return uuid;
    }

}