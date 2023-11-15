package ru.clevertec.animal.service.impl;

import ru.clevertec.animal.dao.BaseDao;
import ru.clevertec.animal.dao.impl.AnimalDao;
import ru.clevertec.animal.data.AnimalDto;
import ru.clevertec.animal.entity.Animal;
import ru.clevertec.animal.exception.AnimalNotFoundException;
import ru.clevertec.animal.exception.ValidatorException;
import ru.clevertec.animal.mapper.AnimalMapper;
import ru.clevertec.animal.mapper.AnimalMapperImpl;
import ru.clevertec.animal.service.BaseService;
import ru.clevertec.animal.validator.ObjectValidator;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AnimalServiceImpl implements BaseService<AnimalDto> {

    private final BaseDao<UUID, Animal> animalDao;
    private final AnimalMapper mapper;

    public AnimalServiceImpl() {
        animalDao = new AnimalDao();
        mapper = new AnimalMapperImpl();
    }

    /**
     * ищет продукт по идентификатору
     *
     * @param uuid идентификатор продукта
     * @return найденный продукт
     * @throws AnimalNotFoundException если не найден
     */
    @Override
    public AnimalDto get(UUID uuid) {
        try {
            return mapper.toAnimalDTO(animalDao.findEntityById(uuid));
        } catch (Exception e) {
            throw new AnimalNotFoundException(uuid);
        }
    }

    /**
     * Возвращает все существующие животные
     *
     * @return лист с информацией о животных
     */
    @Override
    public List<AnimalDto> getAll() {
        return animalDao.findAll().stream()
                .map(mapper::toAnimalDTO)
                .collect(Collectors.toList());
    }

    /**
     * Создаёт новый продукт из DTO
     *
     * @param animalDto DTO с информацией о создании
     * @return идентификатор созданного продукта
     */
    @Override
    public void create(AnimalDto animalDto) { /// void???
        try {
            if (ObjectValidator.validate(animalDto)) {
                animalDao.create(mapper.toAnimal(animalDto));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Обновляет уже существующий продукт из информации полученной в DTO
     *
     * @param uuid      идентификатор продукта для обновления
     * @param animalDto DTO с информацией об обновлении
     */
    @Override
    public void update(UUID uuid, AnimalDto animalDto) {
        try {
            if (ObjectValidator.validate(animalDto)) {
                Animal animalUpdate = animalDao.findEntityById(uuid);
                animalDao.update(mapper.merge(animalUpdate, animalDto));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new AnimalNotFoundException(uuid);
        }
    }

    /**
     * Удаляет существующий продукт
     *
     * @param uuid идентификатор продукта для удаления
     */
    @Override
    public void delete(UUID uuid) {
        animalDao.delete(uuid);
    }
}
