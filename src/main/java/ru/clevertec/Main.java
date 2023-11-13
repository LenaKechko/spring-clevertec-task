package ru.clevertec;

import ru.clevertec.entity.Animal;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Animal animal = new Animal(UUID.randomUUID(), "name", "type", "class", 1000.0, 1.5, 40.0);
        System.out.println(animal.toString());
    }
}