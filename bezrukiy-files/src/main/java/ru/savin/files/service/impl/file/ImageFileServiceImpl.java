package ru.savin.files.service.impl.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.files.config.FilesConfig;
import ru.savin.files.service.abstracts.AbstractFileService;
import ru.savin.files.service.FileService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ImageFileServiceImpl extends AbstractFileService implements FileService {
    private static final String NOT_FOUND = "Изображение не найдено";

    public ImageFileServiceImpl(FilesConfig filesConfig) {
        super(filesConfig);
    }

    @Override
    public byte[] get(String name) throws IOException {
        if (name == null) {
            throw new BezrukiyRuntimeException("Неуказано название изображения");
        }

        Path path = Paths.get(createPathForImage(name));
        if (!isExistFile(path)) {
            throw new BezrukiyRuntimeException(NOT_FOUND);
        }

        byte[] bytesRead;
        try (InputStream inputStream = new FileInputStream(path.toString())) {
            bytesRead = inputStream.readAllBytes();
        }

        return bytesRead;
    }

    @Override
    public void save(MultipartFile file, String name) throws IOException {
        Objects.requireNonNull(file, "Невозможно загрузить изображение, т.к. оно не было загружено");

        Path path = Paths.get(createPathForImage(name));
        if (isExistFile(path)) {
            throw new BezrukiyRuntimeException("Изображение уже загружено");
        }

        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(path.toString())) {
            outputStream.write(inputStream.readAllBytes());
        }
    }

    @Override
    public void update(MultipartFile file, String oldName, String newName) throws IOException {
        Objects.requireNonNull(file, "Невозможно обновить изображение, т.к. оно не было загружено");

        Path source = Paths.get(createPathForImage(oldName));
        if (!isExistFile(source)) {
            throw new BezrukiyRuntimeException(NOT_FOUND);
        }

        Path target = Paths.get(createPathForImage(newName));

        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(source.toString())) {
            outputStream.write(inputStream.readAllBytes());
        }

        Files.move(source, target);
    }

    @Override
    public String delete(String name) throws IOException {
        Path path = Paths.get(createPathForImage(name));

        if (!isExistFile(path)) {
            throw new BezrukiyRuntimeException(NOT_FOUND);
        }

        if (Files.deleteIfExists(path)) {
            return "Изображение удалено";
        }

        return "Изображение не может быть удалено";
    }
}
