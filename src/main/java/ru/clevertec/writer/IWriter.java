package ru.clevertec.writer;

public interface IWriter<T> {

    String DEST_PATH = "\\check";

    String createFile(String caption, T entity);
}
