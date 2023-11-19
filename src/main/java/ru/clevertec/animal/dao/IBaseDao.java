package ru.clevertec.animal.dao;

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
     * Метод для нахождение сущности из БД по id
     *
     * @param id объекта
     * @return объект сущности
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
     * @return true/false - успешное выполнение операции или нет
     */
    boolean delete(K id);

    /**
     * Метод для занесения сущности в БД
     *
     * @param entity
     * @return true/false - успешное выполнение операции или нет
     */
    boolean create(T entity);

    /**
     * Метод для изменения сущности в БД
     *
     * @param entity
     * @return true/false - успешное выполнение операции или нет
     */
    boolean update(T entity);
}
