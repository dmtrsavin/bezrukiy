package ru.savin.files.service.abstracts;

import lombok.RequiredArgsConstructor;
import ru.savin.files.config.FilesConfig;
import ru.savin.files.entity.enums.FileType;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Общие свойства для чтения и записи файлов.
 */
@RequiredArgsConstructor
public abstract class AbstractFileService {
    private static final String DOT = ".";
    private final FilesConfig filesConfig;

    /**
     * Проверка, что файл существует.
     *
     * @param nameFile  название файла.
     * @return      {@link Boolean}
     */
    protected boolean isExistFile(Path nameFile) {
        return Files.exists(nameFile);
    }

    /**
     * Создание пути для логотипа/авы.
     *
     * @param nameFile  название файла.
     * @return      {@link String}
     */
    protected String createPathForLogo(String nameFile) {
        StringBuilder builder = new StringBuilder();
        builder.append(filesConfig.getLogo()).append(nameFile).append(DOT).append(FileType.LOGO.getExtension());
        return builder.toString();
    }

    /**
     * Создание пути для изображения.
     *
     * @param nameFile  название файла.
     * @return      {@link String}
     */
    protected String createPathForImage(String nameFile) {
        StringBuilder builder = new StringBuilder();
        builder.append(filesConfig.getImage()).append(nameFile).append(DOT).append(FileType.IMAGE.getExtension());
        return builder.toString();
    }

    /**
     * Создание пути для текста.
     *
     * @param nameFile  название файла.
     * @return      {@link String}
     */
    protected String createPathForText(String nameFile) {
        StringBuilder builder = new StringBuilder();
        builder.append(filesConfig.getInfo()).append(nameFile).append(DOT).append(FileType.TEXT.getExtension());
        return builder.toString();
    }
}
