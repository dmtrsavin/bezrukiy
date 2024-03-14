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
public class LogoFileServiceImpl extends AbstractFileService implements FileService {
    private static final String NOT_FOUND = "Логотип не найден";

    public LogoFileServiceImpl(FilesConfig filesConfig) {
        super(filesConfig);
    }

    @Override
    public byte[] get(String name) throws IOException {
        if (name == null) {
            throw new BezrukiyRuntimeException("Неуказано название логотипа");
        }

        Path path = Paths.get(createPathForLogo(name));
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
        Objects.requireNonNull(file, "Невозможно сохранить логотип, т.к. он не был загружен");

        Path path = Paths.get(createPathForLogo(name));
        if (isExistFile(path)) {
            throw new BezrukiyRuntimeException("Логотип уже создан");
        }

        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(path.toString())) {
                outputStream.write(inputStream.readAllBytes());
        }
    }

    @Override
    public void update(MultipartFile file, String oldName, String newName) throws IOException {
        Objects.requireNonNull(file, "Невозможно обновить логотип, т.к. он не был загружен");

        Path source = Paths.get(createPathForLogo(oldName));
        if (!isExistFile(source)) {
            throw new BezrukiyRuntimeException(NOT_FOUND);
        }

        Path target = Paths.get(createPathForLogo(newName));

        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(source.toString())) {
            outputStream.write(inputStream.readAllBytes());
        }

        Files.move(source, target);
    }

    @Override
    public String delete(String name) throws IOException {
        Path path = Paths.get(createPathForLogo(name));

        if (!isExistFile(path)) {
            throw new BezrukiyRuntimeException(NOT_FOUND);
        }

        if (Files.deleteIfExists(path)) {
            return "Логотип удален";
        }

        return "Логотип не может быть удален";
    }
}
