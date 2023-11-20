package ru.clevertec.util;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.animal.connectionDB.MyConnection;
import ru.clevertec.animal.data.AnimalDto;
import ru.clevertec.animal.entity.Animal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class AnimalTestDataForService {

    static Animal animalTest;
    static List<Animal> animalsTest = new ArrayList<>();

    static {
        Connection connection = MyConnection.getConnectionDB();
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
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Builder.Default
    private UUID uuid = animalTest.getId();

    @Builder.Default
    private String name = animalTest.getName();

    @Builder.Default
    private String typeOfAnimal = animalTest.getTypeOfAnimal();


    @Builder.Default
    private String classOfAnimal = animalTest.getClassOfAnimal();

    @Builder.Default
    private double weight = animalTest.getWeight();

    @Builder.Default
    private double height = animalTest.getHeight();

    @Builder.Default
    private double speed = animalTest.getSpeed();

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
