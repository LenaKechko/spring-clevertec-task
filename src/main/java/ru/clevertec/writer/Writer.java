package ru.clevertec.writer;

public class Writer<T> {
    /**
     * Поле типа интерфейса
     */
    private final IWriter<T> writer;

    /**
     * Конструктор иниацилизующий поле класса
     *
     * @param writer объект типа реализующий интерфейс IWriter
     */
    public Writer(IWriter writer) {
        this.writer = writer;
    }

    /**
     * Метод запускающий запись в файлы различного типа
     *
     * @param entity сущность для записи
     */
    public void runWriter(String text, T entity) {
        writer.createFile(text, entity);
    }
}
