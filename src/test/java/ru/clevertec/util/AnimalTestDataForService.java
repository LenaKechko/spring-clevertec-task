package ru.clevertec.util;

import lombok.Data;
import ru.clevertec.config.connection.MySingletonConnection;
import ru.clevertec.dto.AnimalDto;
import ru.clevertec.entity.Animal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
//@Builder(setterPrefix = "with")
public class AnimalTestDataForService {

    private Animal animalTest;
    private List<Animal> animalsTest = new ArrayList<>();

    private UUID uuid;
    private String name;
    private String typeOfAnimal;
    private String classOfAnimal;
    private double weight;
    private double height;
    private double speed;

    public AnimalTestDataForService() {
        Connection connection = MySingletonConnection.INSTANCE.getConnectionDB();
        String SQL_SELECT_ALL_ANIMALS = "SELECT * FROM animals";
        try (
                Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_ANIMALS);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                String name = rs.getString(2);
                String typeOfAnimal = rs.getString(3);
                String classOfAnimal = rs.getString(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double speed = rs.getDouble(7);
                animalsTest.add(new Animal(id, name, typeOfAnimal, classOfAnimal, weight, height, speed));
            }
            animalTest = animalsTest.get((int) (Math.random() * (animalsTest.size())));
            uuid = animalTest.getId();
            name = animalTest.getName();
            typeOfAnimal = animalTest.getTypeOfAnimal();
            classOfAnimal = animalTest.getClassOfAnimal();
            weight = animalTest.getWeight();
            height = animalTest.getHeight();
            speed = animalTest.getSpeed();
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Animal> buildListAnimals() {
        return animalsTest;
    }

    public List<AnimalDto> buildListAnimalsDto() {
        return animalsTest.stream()
                .map(animal ->
                        new AnimalDto(animal.getName(),
                                animal.getTypeOfAnimal(),
                                animal.getClassOfAnimal(),
                                animal.getWeight(),
                                animal.getHeight(),
                                animal.getSpeed())
                ).toList();
    }

    public Animal buildAnimal() {
        return new Animal(animalTest.getId(), animalTest.getName(),
                animalTest.getTypeOfAnimal(), animalTest.getClassOfAnimal(),
                animalTest.getWeight(), animalTest.getHeight(), animalTest.getSpeed());
    }

    public AnimalDto buildAnimalDto() {
        return new AnimalDto(animalTest.getName(),
                animalTest.getTypeOfAnimal(), animalTest.getClassOfAnimal(),
                animalTest.getWeight(), animalTest.getHeight(), animalTest.getSpeed());
    }
}
