package ru.clevertec;

import ru.clevertec.animal.data.AnimalDto;
import ru.clevertec.animal.entity.Animal;
import ru.clevertec.animal.proxy.cache.IBaseCache;
import ru.clevertec.animal.proxy.cache.imp.LRUCache;
import ru.clevertec.animal.service.IBaseService;
import ru.clevertec.animal.service.impl.AnimalServiceImpl;

import java.lang.reflect.Field;
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
        IBaseService<AnimalDto> service = new AnimalServiceImpl();
//        service.get(UUID.fromString("657e186a-1a01-48a9-a839-116b81b85057"));
//
        System.out.println(service.get(UUID.fromString("657e186a-1a01-48a9-a839-116b81b85057")));
        service.getAll().stream()
                .forEach(System.out::println);
        service.create(animalDto);
        service.getAll().stream()
                .forEach(System.out::println);
        animalDto.setName("Страус");
        service.getAll().stream()
                .forEach(System.out::println);
        service.update(UUID.fromString("318f55d9-607c-40d2-930a-2329b4dc5304"), animalDto);
        service.getAll().stream()
                .forEach(System.out::println);
        service.delete(UUID.fromString("318f55d9-607c-40d2-930a-2329b4dc5304"));
        service.getAll().stream()
                .forEach(System.out::println);
    }

}