package ru.clevertec.writer;

import java.util.Objects;

public interface IWriter<T> {

    //    String DEST_PATH = "printInfo//print";
    String DEST_PATH = "check";

    String createFile(String caption, T entity);
}
