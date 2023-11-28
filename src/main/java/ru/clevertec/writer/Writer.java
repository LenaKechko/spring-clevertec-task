package ru.clevertec.writer;

/**
 * Класс для вызова соответствующего класса,
 * осуществляющего запись в файл
 *
 * @author Лена Кечко
 * */

public class Writer<T> {

    /**
     * Поле типа интерфейса
     */
    private final IWriter<T> writer;

    /**
     * Конструктор иниацилизующий поле класса в зависимости есть шаблон или нет
     *
     * @param writer отвечает за тип файла, в который будем записывать
     */
    public Writer(IWriter<T> writer) {
        this.writer = writer;
    }

    /**
     * Метод запускающий запись в файлы различного типа
     *
     * @param caption заголовок содержания документа
     * @param entity  сущность для записи
     */
    public void runWriter(String caption, T entity) {
        writer.createFile(caption, entity);
    }
}
