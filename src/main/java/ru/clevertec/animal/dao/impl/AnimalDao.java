package ru.clevertec.animal.dao.impl;

import ru.clevertec.animal.connectionDB.MyConnection;
import ru.clevertec.animal.dao.BaseDao;
import ru.clevertec.animal.entity.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnimalDao implements BaseDao<UUID, Animal> {

    private final Connection connection = MyConnection.getConnectionDB();

    /**
     * Запрос на вывод всех данных из таблицы
     */
    String SQL_SELECT_ALL_ANIMALS = "SELECT * FROM animals";
    /**
     * Запрос на вывод данных по известному id
     */
    String SQL_SELECT_ANIMAL_BY_ID = "SELECT * FROM animals WHERE id = ?";

    /**
     * Запрос на удаление данных из таблицы БД по известному id
     */
    public static final String SQL_DELETE_ANIMAL_ID = "DELETE FROM animals WHERE id = ?";

    /**
     * Запрос на запись данных в таблицу БД
     */
    public static final String SQL_INSERT_ANIMAL =
            "INSERT INTO animals(id, name, type_of_animal, class_of_animal, weight, height, speed) " +
                    "VALUES (gen_random_uuid(), ?, ?, ?, ?, ?, ?)";
    /**
     * Запрос на изменение данных из таблицы БД по id
     */
    public static final String SQL_UPDATE_ANIMAL =
            "UPDATE animals SET name = ?, type_of_animal = ?, class_of_animal = ?, weight = ?, height = ?, speed = ? " +
                    "WHERE id = ?";

    @Override
    public List<Animal> findAll() {
        List<Animal> animals = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_ANIMALS);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                String name = rs.getString(2);
                String typeOfAnimal = rs.getString(3);
                String classOfAnimal = rs.getString(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double speed = rs.getDouble(7);
                animals.add(new Animal(id, name, typeOfAnimal, classOfAnimal, weight, height, speed));
            }
            animals.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return animals;
    }

    @Override
    public Animal findEntityById(UUID id) {
        Animal animal = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ANIMAL_BY_ID)) {
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String typeOfAnimal = rs.getString("type_of_animal");
                String classOfAnimal = rs.getString("class_of_animal");
                double weight = rs.getDouble("weight");
                double height = rs.getDouble("height");
                double speed = rs.getDouble("speed");
                animal = new Animal(id, name, typeOfAnimal, classOfAnimal, weight, height, speed);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return animal;
    }

    @Override
    public boolean delete(UUID id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ANIMAL_ID)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean create(Animal animal) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ANIMAL)) {
            preparedStatement.setString(1, animal.getName());
            preparedStatement.setString(2, animal.getTypeOfAnimal());
            preparedStatement.setString(3, animal.getClassOfAnimal());
            preparedStatement.setDouble(4, animal.getWeight());
            preparedStatement.setDouble(5, animal.getHeight());
            preparedStatement.setDouble(6, animal.getSpeed());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Animal animal) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ANIMAL)) {
            preparedStatement.setString(1, animal.getName());
            preparedStatement.setString(2, animal.getTypeOfAnimal());
            preparedStatement.setString(3, animal.getClassOfAnimal());
            preparedStatement.setDouble(4, animal.getWeight());
            preparedStatement.setDouble(5, animal.getHeight());
            preparedStatement.setDouble(6, animal.getSpeed());
            preparedStatement.setObject(7, animal.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
