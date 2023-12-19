package ru.clevertec.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.dto.AnimalDto;
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
public class AnimalServlet extends HttpServlet {

    private final IBaseService<AnimalDto> service = new AnimalServiceImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid"); //"b1f6bb5f-c6c4-45f9-9024-5b2d6938f840"
        String json;

        if (uuid == null) {
            String pageParameter = req.getParameter("page");
            int page = 1;
            if (pageParameter != null)
                page = Integer.parseInt(pageParameter);
            List<AnimalDto> animals = service.getAll(page, 20);
            json = objectMapper.writeValueAsString(animals);
        } else {
            AnimalDto animal = service.get(UUID.fromString(uuid));
            json = objectMapper.writeValueAsString(animal);
        }

        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String temp;
        String name = req.getParameter("name");
        String typeOfAnimal = req.getParameter("typeOfAnimal");
        String classOfAnimal = req.getParameter("classOfAnimal");
        double weight = (temp = req.getParameter("weight")) == null ? 0.0 : Double.parseDouble(temp);
        double height = (temp = req.getParameter("height")) == null ? 0.0 : Double.parseDouble(temp);
        double speed = (temp = req.getParameter("speed")) == null ? 0.0 : Double.parseDouble(temp);
        AnimalDto animalDto = new AnimalDto(name, typeOfAnimal, classOfAnimal, weight, height, speed);
        UUID uuid = service.create(animalDto);
        if (uuid != null) {
            try (PrintWriter out = resp.getWriter()) {
                out.write(String.valueOf(uuid));
                resp.setStatus(200);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String temp;
        String uuid = req.getParameter("uuid");
        String name = req.getParameter("name");
        String typeOfAnimal = req.getParameter("typeOfAnimal");
        String classOfAnimal = req.getParameter("classOfAnimal");
        double weight = (temp = req.getParameter("weight")) == null ? 0.0 : Double.parseDouble(temp);
        double height = (temp = req.getParameter("height")) == null ? 0.0 : Double.parseDouble(temp);
        double speed = (temp = req.getParameter("speed")) == null ? 0.0 : Double.parseDouble(temp);
        AnimalDto animalDto = new AnimalDto(name, typeOfAnimal, classOfAnimal, weight, height, speed);

        service.update(UUID.fromString(uuid), animalDto);

        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String uuid = req.getParameter("uuid");

        service.delete(UUID.fromString(uuid));

        resp.setStatus(200);
    }

}