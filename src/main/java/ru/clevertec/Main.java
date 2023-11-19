package ru.clevertec;

import ru.clevertec.animal.data.AnimalDto;
import ru.clevertec.animal.entity.Animal;
import ru.clevertec.animal.mapper.AnimalMapper;
import ru.clevertec.animal.mapper.AnimalMapperImpl;
import ru.clevertec.animal.proxy.cache.IBaseCache;
import ru.clevertec.animal.proxy.cache.imp.LRUCache;
import ru.clevertec.animal.service.IBaseService;
import ru.clevertec.animal.service.impl.AnimalServiceImpl;

import java.lang.reflect.Field;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
//        UUID uuid = UUID.randomUUID();
//        Animal animal = new Animal (uuid, "Animal", "type", "class", 1000.0, 1.5, 0.0);
//        AnimalMapper mapper = new AnimalMapperImpl();
        AnimalDto animalDto = new AnimalDto("Animal", "type", "class", 1000.0, 1.5, 40.0);
        IBaseService<AnimalDto> service = new AnimalServiceImpl();
        System.out.println(service.get(UUID.fromString("7c630ffa-a5fb-45c1-9c19-decc01800a46")));
        service.getAll().stream()
                .forEach(System.out::println);
        service.create(animalDto);
        service.getAll().stream()
                .forEach(System.out::println);
        System.out.println(service.get(UUID.fromString("68042654-9ef0-4263-9ab0-5762b122dc4a")));
        animalDto.setName("Страус");
        service.getAll().stream()
                .forEach(System.out::println);
        System.out.println(service.get(UUID.fromString("4457a195-9472-4719-b624-c4b076752b9c")));
        System.out.println(service.get(UUID.fromString("68042654-9ef0-4263-9ab0-5762b122dc4a")));
//        service.update(UUID.fromString("318f55d9-607c-40d2-930a-2329b4dc5304"), animalDto);
//        service.getAll().stream()
//                .forEach(System.out::println);
//        service.delete(UUID.fromString("318f55d9-607c-40d2-930a-2329b4dc5304"));
//        service.getAll().stream()
//                .forEach(System.out::println);
    }

}