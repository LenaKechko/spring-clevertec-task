package ru.clevertec.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.dao.IBaseDao;
import ru.clevertec.data.AnimalDto;
import ru.clevertec.entity.Animal;
import ru.clevertec.exception.AnimalNotFoundException;
import ru.clevertec.exception.ValidatorException;
import ru.clevertec.mapper.AnimalMapper;
import ru.clevertec.util.AnimalTestDataForService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {

    @Mock
    private AnimalMapper mapper;
    @Mock
    private IBaseDao<UUID, Animal> animalDao;
    @InjectMocks
    private AnimalServiceImpl service;

    private AnimalTestDataForService testData;

    @BeforeEach
    void setUp() {
        testData = new AnimalTestDataForService();
    }

    @Test
    void getShouldReturnAnimalDto() {
        // given

        UUID uuid = testData.getUuid();
        AnimalDto expected = testData.buildAnimalDto();

        // when
        AnimalDto actual = service.get(uuid);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(AnimalDto.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(AnimalDto.Fields.typeOfAnimal, expected.getTypeOfAnimal())
                .hasFieldOrPropertyWithValue(AnimalDto.Fields.classOfAnimal, expected.getClassOfAnimal())
                .hasFieldOrPropertyWithValue(AnimalDto.Fields.weight, expected.getWeight())
                .hasFieldOrPropertyWithValue(AnimalDto.Fields.height, expected.getHeight())
                .hasFieldOrPropertyWithValue(AnimalDto.Fields.speed, expected.getSpeed());
    }

    @Test
    void getShouldReturnAnimalNotFoundException() {
        // given
        UUID uuid = UUID.randomUUID();

        // when-then
        assertThrows(AnimalNotFoundException.class, () -> service.get(uuid));
    }

    @Test
    void getAllShouldReturnAnimalsListDto() {
        // given
        List<AnimalDto> expectedList = testData.buildListAnimalsDto();

        // when
        List<AnimalDto> actualList = service.getAll();

        // then
        assertEquals(expectedList, actualList);
    }

    @Test
    void createShouldReturnUuidWhenAnimalSave() {
        // given
        AnimalDto animalDto = testData.buildAnimalDto();

        // when
        UUID actualUuid = service.create(animalDto);

        // then
        assertNotNull(actualUuid);
    }

    @Test
    void createShouldReturnValidatorException() {
        // given
        AnimalDto animalDto = testData.buildAnimalDto();
        animalDto.setWeight(0.0);

        // when-then
        assertThrows(ValidatorException.class, () -> service.create(animalDto));
    }

    @Test
    void update() {
        // given
        UUID uuid = testData.getUuid();
        Animal animalToUpdate = testData.buildAnimal();
        AnimalDto animalDtoToUpdate = testData.buildAnimalDto();
        animalDtoToUpdate.setClassOfAnimal("Новый класс");
        Animal expected = testData.buildAnimal();
        expected.setClassOfAnimal("Новый класс");

        doReturn(expected)
                .when(mapper)
                .merge(animalToUpdate, animalDtoToUpdate);

        doReturn(Optional.of(animalToUpdate))
                .when(animalDao)
                .findEntityById(uuid);

        // when
        service.update(uuid, animalDtoToUpdate);

        // then
        verify(animalDao).update(expected);
    }

    @Test
    void delete() {
        // given
        UUID uuid = testData.getUuid();

        // when
        service.delete(uuid);

        // then
        verify(animalDao).delete(uuid);
    }
}