package ru.clevertec;

import ru.clevertec.animal.connection.MyConnection;
import ru.clevertec.animal.dao.impl.AnimalDao;
import ru.clevertec.animal.data.AnimalDto;
import ru.clevertec.animal.entity.Animal;
import ru.clevertec.animal.validator.ObjectValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Animal animal = new Animal(UUID.randomUUID(), "name", "type", "class", 1000.0, 1.5, 0.0);
        AnimalDto animalDto = new AnimalDto("Слон", "type", "class", 100.0, 1.5, 40.0);
//        System.out.println(animal.toString());
//        System.out.println(animalDto.toString());
//        AnimalMapper mapper = new AnimalMapperImpl();
//        System.out.println(mapper.toAnimalDTO(animal).toString());
//        System.out.println(mapper.toAnimal(animalDto).toString());
//        System.out.println(mapper.merge(animal, animalDto).toString());
//        System.out.println(ObjectValidator.validate(animal));
//        System.out.println(ObjectValidator.validate(animalDto));
        AnimalDao animalDao = new AnimalDao();
        animalDao.setConnection(MyConnection.getConnectionDB());
        System.out.println(animalDao.findAll());
    }
}