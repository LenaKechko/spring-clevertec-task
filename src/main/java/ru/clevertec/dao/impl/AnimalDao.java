package ru.clevertec.dao.impl;

import ru.clevertec.config.connection.MySingletonConnection;
import ru.clevertec.dao.IBaseDao;
import ru.clevertec.entity.Animal;
import ru.clevertec.proxy.annotation.Delete;
import ru.clevertec.proxy.annotation.GetById;
import ru.clevertec.proxy.annotation.Put;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AnimalDao implements IBaseDao<UUID, Animal> {

    private final Connection connection = MySingletonConnection.INSTANCE.getConnectionDB();

    /**
     * Запрос на вывод всех данных из таблицы
     */
    String SQL_SELECT_ALL_ANIMALS =
            "SELECT * FROM animals";

    /**
     * Запрос на вывод 20 записей из таблицы (по странично)
     */
    String SQL_SELECT_ANIMALS =
            "SELECT * FROM animals LIMIT ? OFFSET ?";

    /**
     * Запрос на вывод данных по известному id
     */
    String SQL_SELECT_ANIMAL_BY_ID = "SELECT * FROM animals WHERE id = ?";

    /**
     * Запрос на id по известным данным
     */
    String SQL_SELECT_ID_BY_ANIMAL = """
            SELECT id FROM animals WHERE name = ? and type_of_animal = ? and
             class_of_animal = ? and weight = ? and height = ? and speed = ?
            """;

    /**
     * Запрос на удаление данных из таблицы БД по известному id
     */
    public static final String SQL_DELETE_ANIMAL_ID =
            "DELETE FROM animals WHERE id = ?";

    /**
     * Запрос на запись данных в таблицу БД
     */
    public static final String SQL_INSERT_ANIMAL = """
            INSERT INTO animals(id, name, type_of_animal, class_of_animal, weight, height, speed)
            VALUES (gen_random_uuid(), ?, ?, ?, ?, ?, ?)
            """;
    /**
     * Запрос на изменение данных из таблицы БД по id
     */
    public static final String SQL_UPDATE_ANIMAL = """
            UPDATE animals SET name = ?, type_of_animal = ?, class_of_animal = ?, weight = ?, height = ?, speed = ?
            WHERE id = ?
            """;

    /**
     * Метод для просмотра всех данных из БД
     *
     * @return List объектов сущности
     */
    @Override
    public List<Animal> findAll() {
        List<Animal> animals = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_ANIMALS);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                animals.add(newAnimalFromRequest(id, rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return animals;
    }

    public List<Animal> findAll(int page, int size) {
        List<Animal> animals = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ANIMALS)) {
            statement.setObject(1, size);
            statement.setObject(2, (page - 1) * size);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                animals.add(newAnimalFromRequest(id, rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return animals;
    }

    /**
     * Метод для нахождение сущности из БД по id
     *
     * @param id объекта
     * @return Optinal объект сущности
     */
    @Override
    @GetById
    public Optional<Animal> findEntityById(UUID id) {
        Animal animal = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ANIMAL_BY_ID)) {
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                animal = newAnimalFromRequest(id, rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(animal);
    }


    /**
     * Метод для нахождение id из БД по сущности
     *
     * @param animal сущность
     * @return идентификатор сущности
     */
    @Override
    public UUID findIdByEntity(Animal animal) {
        UUID id = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ID_BY_ANIMAL)) {
            addParameterIntoRequest(animal, preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                id = (UUID) rs.getObject("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    /**
     * Метод для удаления сущности из БД по id
     *
     * @param id объекта
     */
    @Override
    @Delete
    public void delete(UUID id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ANIMAL_ID)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод для занесения сущности в БД
     *
     * @param animal сущность
     * @return true/false - успешное выполнение операции или нет
     */
    @Override
    @Put
    public boolean create(Animal animal) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ANIMAL)) {
            addParameterIntoRequest(animal, preparedStatement);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Метод для изменения сущности в БД
     *
     * @param animal сущность
     */
    @Override
    @Put
    public void update(Animal animal) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ANIMAL)) {
            addParameterIntoRequest(animal, preparedStatement);
            preparedStatement.setObject(7, animal.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addParameterIntoRequest(Animal animal, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, animal.getName());
        preparedStatement.setString(2, animal.getTypeOfAnimal());
        preparedStatement.setString(3, animal.getClassOfAnimal());
        preparedStatement.setDouble(4, animal.getWeight());
        preparedStatement.setDouble(5, animal.getHeight());
        preparedStatement.setDouble(6, animal.getSpeed());
    }

    private static Animal newAnimalFromRequest(UUID id, ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String typeOfAnimal = rs.getString("type_of_animal");
        String classOfAnimal = rs.getString("class_of_animal");
        double weight = rs.getDouble("weight");
        double height = rs.getDouble("height");
        double speed = rs.getDouble("speed");
        return new Animal(id, name, typeOfAnimal, classOfAnimal, weight, height, speed);
    }
}
