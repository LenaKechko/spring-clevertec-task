package ru.clevertec.animal.validator;

import org.junit.jupiter.api.Test;
import ru.clevertec.animal.data.AnimalDto;
import ru.clevertec.animal.exception.ValidatorException;
import ru.clevertec.animal.validator.util.AnimalTestData;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectValidatorTest {

    @Test
    void validateShouldReturnTrue() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder().build().buildProductDto();

        // when-then
        assertTrue(ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldReturnTrueWithEmptyTypeOfAnimal() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withTypeOfAnimal("")
                .build()
                .buildProductDto();

        // when-then
        assertTrue(ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldReturnTrueWithEmptyClassOfAnimal() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withClassOfAnimal("")
                .build()
                .buildProductDto();

        // when-then
        assertTrue(ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithoutName() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withName("")
                .build()
                .buildProductDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithBigName() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withName("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .build()
                .buildProductDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithBigTypeOfAnimal() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withTypeOfAnimal("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .build()
                .buildProductDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithBigClassOfAnimal() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withClassOfAnimal("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .build()
                .buildProductDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithZeroWeight() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withWeight(0.0)
                .build()
                .buildProductDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithNegativeWeight() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withWeight(-4.0)
                .build()
                .buildProductDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithZeroHeight() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withHeight(0.0)
                .build()
                .buildProductDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithNegativeHeight() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withHeight(-4.0)
                .build()
                .buildProductDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithZeroSpeed() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withSpeed(0.0)
                .build()
                .buildProductDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }

    @Test
    void validateShouldThrowValidateExceptionWithNegativeSpeed() throws IllegalAccessException {
        // given
        AnimalDto animalDto = AnimalTestData.builder()
                .withSpeed(-4.0)
                .build()
                .buildProductDto();

        // when-then
        assertThrows(ValidatorException.class, () -> ObjectValidator.validate(animalDto));
    }
}