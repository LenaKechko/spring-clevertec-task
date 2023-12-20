package ru.clevertec.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.dto.AnimalDto;
import ru.clevertec.exception.AnimalNotFoundException;
import ru.clevertec.exception.ValidatorException;
import ru.clevertec.service.IBaseService;
import ru.clevertec.service.impl.AnimalServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "Animal", urlPatterns = "/animal")
@Slf4j
public class AnimalServlet extends HttpServlet {

    private final IBaseService<AnimalDto> service = new AnimalServiceImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid");
        String json;

        if (uuid == null) {
            String pageParameter = req.getParameter("page");
            int page = 1;
            if (pageParameter != null)
                page = Integer.parseInt(pageParameter);
            List<AnimalDto> animals = service.getAll(page, 20);
            json = objectMapper.writeValueAsString(animals);
            successfulProcess(resp, json);
        } else {
            try {
                AnimalDto animal = service.get(UUID.fromString(uuid));
                json = objectMapper.writeValueAsString(animal);
                successfulProcess(resp, json);
            } catch (AnimalNotFoundException e) {
                failProcess(resp, e.getMessage());
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AnimalDto animalDto = parseRequest(req);
        try {
            UUID uuid = service.create(animalDto);
            successfulProcess(resp, String.valueOf(uuid));
        } catch (ValidatorException | AnimalNotFoundException e) {
            failProcess(resp, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid");
        AnimalDto animalDto = parseRequest(req);
        try {
            service.update(UUID.fromString(uuid), animalDto);
            successfulProcess(resp, "Success update entity");
        } catch (ValidatorException e) {
            failProcess(resp, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String uuid = req.getParameter("uuid");

        service.delete(UUID.fromString(uuid));

        resp.setStatus(200);
    }

    private void successfulProcess(HttpServletResponse resp, String obj) throws IOException {
        try (PrintWriter out = resp.getWriter()) {
            out.write(obj);
            resp.setStatus(200);
            log.info("Process successful");
        }
    }

    private void failProcess(HttpServletResponse resp, String obj) {
        resp.setStatus(404);
        log.info("Process fail. " + obj);
    }

    private AnimalDto parseRequest(HttpServletRequest req) {
        String temp;
        String name = req.getParameter("name");
        String typeOfAnimal = req.getParameter("typeOfAnimal");
        String classOfAnimal = req.getParameter("classOfAnimal");
        double weight = (temp = req.getParameter("weight")) == null ? 0.0 : Double.parseDouble(temp);
        double height = (temp = req.getParameter("height")) == null ? 0.0 : Double.parseDouble(temp);
        double speed = (temp = req.getParameter("speed")) == null ? 0.0 : Double.parseDouble(temp);
        return new AnimalDto(name, typeOfAnimal, classOfAnimal, weight, height, speed);
    }

}