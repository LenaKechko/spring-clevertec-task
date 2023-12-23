package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.dao.IBaseDao;
import ru.clevertec.dto.AnimalDto;
import ru.clevertec.entity.Animal;
import ru.clevertec.exception.AnimalNotFoundException;
import ru.clevertec.exception.ValidatorException;
import ru.clevertec.mapper.AnimalMapper;
import ru.clevertec.service.IBaseService;
import ru.clevertec.validator.ObjectValidator;
import ru.clevertec.writer.Writer;
import ru.clevertec.writer.impl.WriterPdf;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Класс-сервис для работы с crud-операциями
 *
 * @author Кечко Елена
 */
@RequiredArgsConstructor
public class AnimalServiceImpl implements IBaseService<AnimalDto> {

    /**
     * Поле для работы с dao
     */
    private final IBaseDao<UUID, Animal> animalDao;
    /**
     * Поле для работы с mapper'ом
     */
    private final AnimalMapper mapper;

    /**
     * ищет животное по идентификатору
     *
     * @param uuid идентификатор животное
     * @return найденное животное
     * @throws AnimalNotFoundException если не найден
     */
    @Override
    public AnimalDto get(UUID uuid) {
        Animal animal = animalDao.findEntityById(uuid)
                .orElseThrow(() -> new AnimalNotFoundException(uuid));
        AnimalDto animalDto = mapper.toAnimalDTO(animal);
        Writer<AnimalDto> writer = new Writer<>(new WriterPdf<>());
        writer.runWriter("Информация по животному с кодом: " + uuid, animalDto);
        return animalDto;
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
     * Возвращает 20 животных
     *
     * @return лист с информацией о животных
     */
    @Override
    public List<AnimalDto> getAll(int page, int size) {
        return animalDao.findAll(page, size).stream()
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
    public UUID create(AnimalDto animalDto) throws ValidatorException {
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
        } catch (IllegalAccessException e) {
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
    public void update(UUID uuid, AnimalDto animalDto) throws ValidatorException{
        try {
            if (ObjectValidator.validate(animalDto)) {
                Animal animalUpdate = animalDao.findEntityById(uuid).orElseThrow(() -> new AnimalNotFoundException(uuid));
                animalDao.update(mapper.merge(animalUpdate, animalDto));
            }
//        } catch (ValidatorException e) {
//            System.out.println(e.getMessage());
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
