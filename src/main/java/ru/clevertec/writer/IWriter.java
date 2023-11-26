package ru.clevertec.writer;

public interface IWriter<T> {

    void createFile(String text, T entity);
}
