package ru.clevertec.config;

import lombok.Getter;
import ru.clevertec.dao.IBaseDao;
import ru.clevertec.dao.impl.AnimalDao;
import ru.clevertec.dto.AnimalDto;
import ru.clevertec.entity.Animal;
import ru.clevertec.mapper.AnimalMapper;
import ru.clevertec.mapper.AnimalMapperImpl;
import ru.clevertec.service.IBaseService;
import ru.clevertec.service.impl.AnimalServiceImpl;

import java.util.UUID;

public class ConfigService {

    /**
     * Объект DAO. Слой для работы с БД
     */
    private static final IBaseDao<UUID, Animal> animalDao = new AnimalDao();

    /**
     * Объект mapper. Слой для преобразования Animal/AnimalDto
     */
    private static final AnimalMapper animalMapper = new AnimalMapperImpl();

    /**
     * Объект сервиса. Промежуточный слой для работы с БД
     */
    @Getter
    private static final IBaseService<AnimalDto> service = new AnimalServiceImpl(animalDao, animalMapper);

}
