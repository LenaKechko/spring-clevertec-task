package ru.clevertec.service;

import java.util.List;
import java.util.UUID;

public interface IBaseService<T> {

    T get(UUID uuid);

    List<T> getAll();

    List<T> getAll(int page, int size);

    UUID create(T animalDto);

    void update(UUID uuid, T animalDto);

    void delete(UUID uuid);

}
