package ru.clevertec.writer;

public interface IWriter<T> {

    String DEST_PATH = "printInfo//print";

    void createFile(String caption, T entity);
}
