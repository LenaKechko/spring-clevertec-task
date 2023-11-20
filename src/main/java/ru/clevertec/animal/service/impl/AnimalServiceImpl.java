package ru.clevertec.animal.service.impl;

import ru.clevertec.animal.dao.IBaseDao;
import ru.clevertec.animal.dao.impl.AnimalDao;
import ru.clevertec.animal.data.AnimalDto;
import ru.clevertec.animal.entity.Animal;
import ru.clevertec.animal.exception.AnimalNotFoundException;
import ru.clevertec.animal.exception.ValidatorException;
import ru.clevertec.animal.mapper.AnimalMapper;
import ru.clevertec.animal.mapper.AnimalMapperImpl;
import ru.clevertec.animal.service.IBaseService;
import ru.clevertec.animal.validator.ObjectValidator;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Класс-сервис для работы с crud-операциями
 *
 * @author Кечко Елена
 */

public class AnimalServiceImpl implements IBaseService<AnimalDto> {

    /**
     * Поле для работы с dao
     */
    private final IBaseDao<UUID, Animal> animalDao;
    /**
     * Поле для работы с mapper'ом
     */
    private final AnimalMapper mapper;

    public AnimalServiceImpl() {
        animalDao = new AnimalDao();
        mapper = new AnimalMapperImpl();
    }

    /**
     * ищет животное по идентификатору
     *
     * @param uuid идентификатор животное
     * @return найденное животное
     * @throws AnimalNotFoundException если не найден
     */
    @Override
    public AnimalDto get(UUID uuid) {
        return mapper.toAnimalDTO(
                animalDao.findEntityById(uuid)
                        .orElseThrow(() -> new AnimalNotFoundException(uuid)));
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
     * Создаёт новое животное из DTO
     *
     * @param animalDto DTO с информацией о создании
     * @return идентификатор созданного животного
     * @throws AnimalNotFoundException если животное не было сохранено
     */
    @Override
    public UUID create(AnimalDto animalDto) {
        try {
            boolean isCreate = false;
            Animal animalToSave = mapper.toAnimal(animalDto);
            if (ObjectValidator.validate(animalDto)) {
                isCreate = animalDao.create(animalToSave);
            }
            if (isCreate) {
                return animalDao.findIdByEntity(animalToSave);
            }
            throw new AnimalNotFoundException(null);
        } catch (IllegalAccessException | ValidatorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Обновляет уже существующее животное из информации полученной в DTO
     *
     * @param uuid      идентификатор животного для обновления
     * @param animalDto DTO с информацией об обновлении
     */
    @Override
    public void update(UUID uuid, AnimalDto animalDto) {
        try {
            if (ObjectValidator.validate(animalDto)) {
                Animal animalUpdate = animalDao.findEntityById(uuid).orElseThrow(() -> new AnimalNotFoundException(uuid));
                animalDao.update(mapper.merge(animalUpdate, animalDto));
            }
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаляет существующий животного
     *
     * @param uuid идентификатор животного для удаления
     */
    @Override
    public void delete(UUID uuid) {
        animalDao.delete(uuid);
    }
}
