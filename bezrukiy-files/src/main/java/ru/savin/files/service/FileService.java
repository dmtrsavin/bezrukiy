package ru.savin.files.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Сервис по работе с файлами.
 */
public interface FileService {

    /**
     * Получение файла.
     *
     * @param name  название файла
     * @return {@link byte[]}
     * @throws IOException  исключение ввода/вывода
     */
    byte[] get(String name) throws IOException;

    void save(MultipartFile file, String name) throws IOException;

    void update(MultipartFile file, String oldName, String newName) throws IOException;

    String delete(String name) throws IOException;
}
