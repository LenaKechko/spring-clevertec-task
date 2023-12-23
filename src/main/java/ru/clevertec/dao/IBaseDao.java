package ru.clevertec.dao;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс Предоставляет список страндартных методов
 * для crud-операций. Параметризированный: на первом месте тип ключа,
 * на втором тип сущности. Содержит protected экземпляр Connection
 * для работы со Statement и PreperadStatement
 *
 * @author Кечко Елена
 */
public interface IBaseDao<K, T> {

    /**
     * Метод для просмотра всех данных из БД
     *
     * @return List объектов сущности
     */
    List<T> findAll();

    /**
     * Метод для просмотра данных по странично
     *
     * @return List объектов сущности
     */
    List<T> findAll(int page, int size);

    /**
     * Метод для нахождение сущности из БД по id
     *
     * @param id объекта
     * @return Optinal объект сущности
     */
    Optional<T> findEntityById(K id);

    /**
     * Метод для нахождение id из БД по сущности
     *
     * @param entity сущность
     * @return идентификатор сущности
     */
    K findIdByEntity(T entity);

    /**
     * Метод для удаления сущности из БД по id
     *
     * @param id объекта
     */
    void delete(K id);

    /**
     * Метод для занесения сущности в БД
     *
     * @param entity сущность
     * @return true/false - успешное выполнение операции или нет
     */
    boolean create(T entity);

    /**
     * Метод для изменения сущности в БД
     *
     * @param entity сущность
     */
    void update(T entity);

}
