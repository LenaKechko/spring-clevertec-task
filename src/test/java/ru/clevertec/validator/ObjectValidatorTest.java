package ru.clevertec.validator;

import org.junit.jupiter.api.Test;
import ru.clevertec.dto.AnimalDto;
import ru.clevertec.exception.ValidatorException;
import ru.clevertec.util.AnimalTestData;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectValidatorTest {

    @Test
    void validateShouldReturnTrue() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder().build().buildAnimalDto();

        // when-then
        assertTrue(ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldReturnTrueWithEmptyTypeOfAnimal() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withTypeOfAnimal("")
                .build()
                .buildAnimalDto();

        // when-then
        assertTrue(ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldReturnTrueWithEmptyClassOfAnimal() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withClassOfAnimal("")
                .build()
                .buildAnimalDto();

        // when-then
        assertTrue(ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithoutName() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withName("")
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithBigName() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withName("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithBigTypeOfAnimal() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withTypeOfAnimal("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithBigClassOfAnimal() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withClassOfAnimal("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithZeroWeight() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withWeight(0.0)
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithNegativeWeight() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withWeight(-4.0)
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithZeroHeight() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withHeight(0.0)
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithNegativeHeight() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withHeight(-4.0)
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithZeroSpeed() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withSpeed(0.0)
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithNegativeSpeed() {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withSpeed(-4.0)
                .build()
                .buildAnimalDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }
}