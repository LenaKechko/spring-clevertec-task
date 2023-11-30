package ru.clevertec.writer;

public interface IWriter<T> {

    String DEST_PATH = "printInfo//print";

    String createFile(String caption, T entity);
}
