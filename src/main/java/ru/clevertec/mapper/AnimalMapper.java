package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.dto.AnimalDto;
import ru.clevertec.entity.Animal;

/**
 * Интерфейс реализующий слой маппинга объектов,
 * преобразование из AnimalDto в Animal и обратно.
 * Инмлементация интерфейса является spring-компонентом
 *
 * @author Кечко Елена
 */
@Mapper(componentModel = "spring")
public interface AnimalMapper {

    /**
     * Маппит DTO в животное без UUID
     *
     * @param animalDTO - DTO для маппинга
     * @return новое животное
     */
    @Mapping(target = "id", ignore = true)
    Animal toAnimal(AnimalDto animalDTO);

    /**
     * Маппит животное в DTO без UUID
     *
     * @param animal существующее животное
     * @return обновлённый продукт
     */
    AnimalDto toAnimalDTO(Animal animal);

    /**
     * Сливает существующее животное с информацией из DTO
     * не меняет идентификатор
     *
     * @param animal    существующее животное
     * @param animalDto информация для обновления
     * @return обновлённое животное
     */
    Animal merge(@MappingTarget Animal animal, AnimalDto animalDto);
}