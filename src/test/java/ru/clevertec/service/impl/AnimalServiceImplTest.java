package ru.clevertec.service.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.dao.IBaseDao;
import ru.clevertec.dto.AnimalDto;
import ru.clevertec.entity.Animal;
import ru.clevertec.exception.AnimalNotFoundException;
import ru.clevertec.exception.ValidatorException;
import ru.clevertec.mapper.AnimalMapper;
import ru.clevertec.util.AnimalTestDataForService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
        Animal animalFromDB = testData.buildAnimal();

        doReturn(Optional.of(animalFromDB))
                .when(animalDao)
                .findEntityById(uuid);

        doReturn(expected)
                .when(mapper)
                .toAnimalDTO(animalFromDB);

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
        List<Animal> animalList = testData.buildListAnimals();

        when(animalDao.findAll()).thenReturn(animalList);

        IntStream.range(0, animalList.size())
                .forEach(index ->
                        doReturn(expectedList.get(index))
                                .when(mapper).toAnimalDTO(animalList.get(index)));

        // when
        List<AnimalDto> actualList = service.getAll();

        // then
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 20", "2, 5", "3, 2"})
    void getAllShouldReturnAnimalsListDtoFromExactPageAndSize(int page, int size) {
        // given
        int startIndex = (page - 1) * size + 1;
        int lastIndex = startIndex + size;
        List<AnimalDto> expectedList = testData.buildListAnimalsDto().subList(startIndex, lastIndex);
        List<Animal> animalList = testData.buildListAnimals().subList(startIndex, lastIndex);

        when(animalDao.findAll(page, size)).thenReturn(animalList);

        IntStream.range(0, animalList.size())
                .forEach(index ->
                        doReturn(expectedList.get(index))
                                .when(mapper).toAnimalDTO(animalList.get(index)));

        // when
        List<AnimalDto> actualList = service.getAll(page, size);

        // then
        assertEquals(expectedList, actualList);
    }

    @SneakyThrows
    @Test
    void createShouldReturnUuidWhenAnimalSave() {
        // given
        AnimalDto animalDtoToSave = testData.buildAnimalDto();
        Animal expectedAnimal = testData.buildAnimal();
        UUID expectedUuid = testData.getUuid();

        when(mapper.toAnimal(animalDtoToSave))
                .thenReturn(expectedAnimal);

        when(animalDao.create(expectedAnimal)).thenReturn(true);
        when(animalDao.findIdByEntity(expectedAnimal)).thenReturn(expectedUuid);

        // when
        UUID actualUuid = service.create(animalDtoToSave);

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