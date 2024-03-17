package ru.savin.files.service;

import org.springframework.web.multipart.MultipartFile;
import ru.savin.files.dto.FileStorageDTO;
import ru.savin.files.dto.FileStorageInfoDTO;
import ru.savin.files.dto.FileStorageUpdateDTO;

import java.io.OutputStream;
import java.util.List;

public interface FileStorageService {

    FileStorageInfoDTO getLogo(String name);

    /**
     * Получить файл по названию.
     *
     * @param name название файла
     * @return {@link OutputStream}
     */
    List<FileStorageInfoDTO> getFiles(String name);

    /**
     * Сохранить файл и данные о нем.
     *
     * @param fileStorageDTO данные с файлом
     */
    void writeFile(FileStorageDTO fileStorageDTO, MultipartFile newFile);

    /**
     * Обновить файл и даннык о нем.
     *
     * @param updateDTO данные с файлом
     */
    void updateFile(FileStorageUpdateDTO updateDTO, MultipartFile updateFile);

    /**
     * Удалить файл и данные о нем.
     *
     * @param name название файла
     * @return {@link String}
     */
    String deleteFile(String name);
}