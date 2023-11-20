package ru.clevertec.animal.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.animal.dao.IBaseDao;
import ru.clevertec.animal.data.AnimalDto;
import ru.clevertec.animal.entity.Animal;
import ru.clevertec.animal.exception.AnimalNotFoundException;
import ru.clevertec.animal.exception.ValidatorException;
import ru.clevertec.animal.mapper.AnimalMapper;
import ru.clevertec.util.AnimalTestData;
import ru.clevertec.util.AnimalTestDataForService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {

    @Mock
    private AnimalMapper mapper;
    @Mock
    private IBaseDao<UUID, Animal> animalDao;
    @InjectMocks
    private AnimalServiceImpl service;

    @BeforeEach
    void setUp() {
        AnimalTestDataForService.builder().build();
    }

    @Test
    void getShouldReturnAnimalDto() {
        // given
        UUID uuid = AnimalTestDataForService.builder().build().getUuid();
        AnimalDto expected = AnimalTestDataForService.builder()
                .build()
                .buildAnimalDto();

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
        List<AnimalDto> expectedList = AnimalTestDataForService.builder()
                .build()
                .buildListAnimalsDto();

        // when
        List<AnimalDto> actualList = service.getAll();

        // then
        assertEquals(expectedList, actualList);
    }

    @Test
    void createShouldReturnUuidWhenAnimalSave() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .build().buildAnimalDto();

        // when
        UUID actualUuid = service.create(animalDto);

        // then
        assertNotNull(actualUuid);
    }

    @Test
    void createShouldReturnValidatorException() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withWeight(0.0)
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> service.create(animalDto));
    }

    @Test
    void update() {
        // given
        UUID uuid = AnimalTestDataForService.builder().build().getUuid();
        AnimalDto animalDtoToUpdate = AnimalTestDataForService.builder()
                .build().buildAnimalDto();
        animalDtoToUpdate.setClassOfAnimal("Новый класс");
        Animal animalToUpdate = AnimalTestDataForService.builder()
                .build().buildAnimal();
        animalToUpdate.setClassOfAnimal("Новый класс");
        Animal expected = AnimalTestDataForService.builder()
                .build().buildAnimal();
        expected.setClassOfAnimal("Новый класс");

        // when
        service.update(uuid, animalDtoToUpdate);

        // then
        verify(animalDao).update(expected);
    }

    @Test
    void delete() {
        // given
        UUID uuid = AnimalTestDataForService.builder().build().getUuid();

        // when
        service.delete(uuid);

        // then
        verify(animalDao).delete(uuid);
    }
}