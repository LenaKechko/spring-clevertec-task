package ru.clevertec;

import ru.clevertec.animal.data.AnimalDto;
import ru.clevertec.animal.entity.Animal;
import ru.clevertec.animal.service.BaseService;
import ru.clevertec.animal.service.impl.AnimalServiceImpl;

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
        BaseService<AnimalDto> service = new AnimalServiceImpl();

//
//        System.out.println(service.get(UUID.fromString("657e186a-1a01-48a9-a839-116b81b85057")));
//        service.create(animalDto);
//        animalDto.setName("Страус");
//        service.update(UUID.fromString("b357dd88-5ff7-4485-bc7e-da81cca3fa6e"), animalDto);
//        service.delete(UUID.fromString("b357dd88-5ff7-4485-bc7e-da81cca3fa6e"));
        System.out.println(service.getAll());
    }
}