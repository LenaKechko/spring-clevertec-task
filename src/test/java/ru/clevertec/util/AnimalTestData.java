package ru.clevertec.util;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.dto.AnimalDto;
import ru.clevertec.entity.Animal;

import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class AnimalTestData {

    @Builder.Default
    private UUID uuid = UUID.fromString("338903f8-ff25-4df4-8348-f8cefc066296");

    @Builder.Default
    private String name = "Слон";

    @Builder.Default
    private String typeOfAnimal = "type";


    @Builder.Default
    private String classOfAnimal = "class";

    @Builder.Default
    private double weight = 100.0;

    @Builder.Default
    private double height = 1.5;

    @Builder.Default
    private double speed = 40.0;

    public Animal buildAnimal() {
        return new Animal(uuid, name, typeOfAnimal, classOfAnimal, weight, height, speed);
    }

    public AnimalDto buildAnimalDto() {
        return new AnimalDto(name, typeOfAnimal, classOfAnimal, weight, height, speed);
    }
}