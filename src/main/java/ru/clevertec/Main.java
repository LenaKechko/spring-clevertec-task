package ru.clevertec;

import ru.clevertec.animal.data.AnimalDto;
import ru.clevertec.animal.service.IBaseService;
import ru.clevertec.animal.service.impl.AnimalServiceImpl;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("--------Содержамое таблицы базы данных-------------");
        IBaseService<AnimalDto> service = new AnimalServiceImpl();
        service.getAll()
                .forEach(System.out::println);
        System.out.println("--------Добавление следующего животного в базу данных-------------");
        AnimalDto animalDto = new AnimalDto("Animal", "type", "class", 1000.0, 1.5, 40.0);
        System.out.println(animalDto);
        UUID uuid = service.create(animalDto);
        System.out.println("--------Содержамое таблицы базы данных-------------");
        service.getAll()
                .forEach(System.out::println);
        System.out.println("--------Изменение-------------");
        System.out.println("Изменим в добавленной записи имя на 'Страус'");
        animalDto.setName("Страус");
        service.update(uuid, animalDto);
        System.out.println("--------Просмотр измененной записи-------------");
        System.out.println(service.get(uuid));
        System.out.println("--------Содержамое таблицы базы данных-------------");
        service.getAll()
                .forEach(System.out::println);
        System.out.println("--------Удаление записи-------------");
        service.delete(uuid);
        System.out.println("--------Содержамое таблицы базы данных-------------");
        service.getAll()
                .forEach(System.out::println);
    }

}