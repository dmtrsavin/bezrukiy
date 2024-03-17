package ru.savin.files.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.files.config.FilesConfig;
import ru.savin.files.dto.FileStorageDTO;
import ru.savin.files.dto.FileStorageInfoDTO;
import ru.savin.files.dto.FileStorageUpdateDTO;
import ru.savin.files.mapper.FileStorageMapper;
import ru.savin.files.service.FileService;
import ru.savin.files.service.FileStorageCrud;
import ru.savin.files.service.FileStorageService;
import ru.savin.files.service.abstracts.AbstractFileStorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileStorageServiceImpl extends AbstractFileStorageService implements FileStorageService {

    private final FileService imageService;
    private final FileService logoService;
    private final FileService textService;
    private final FileStorageMapper fileStorageMapper;
    private final FileStorageCrud crudService;

    public FileStorageServiceImpl(FilesConfig filesConfig,
                                  @Qualifier("imageFileServiceImpl")
                                  FileService imageService,
                                  @Qualifier("logoFileServiceImpl")
                                  FileService logoService,
                                  @Qualifier("textFileServiceImpl")
                                  FileService textService,
                                  FileStorageMapper fileStorageMapper,
                                  FileStorageCrud crudService) {
        super(filesConfig);
        this.imageService = imageService;
        this.logoService = logoService;
        this.textService = textService;
        this.fileStorageMapper = fileStorageMapper;
        this.crudService = crudService;
    }

    @Override
    public FileStorageInfoDTO getLogo(String name) {
        Objects.requireNonNull(name, "Название не указано, для получения логотипа");

        FileStorageDTO fileStorageDTO = crudService.get(name);
        byte[] logo;
        try {
            logo = logoService.get(name);
        } catch (IOException e) {
            throw new BezrukiyRuntimeException("Невозможно получить логотип");
        }

        return fileStorageMapper.mapInfoDTO(fileStorageDTO.name(), logo);
    }

    @Override
    public List<FileStorageInfoDTO> getFiles(String name) {
        Objects.requireNonNull(name, "Название не указано, для получения изображения");

        List<FileStorageInfoDTO> infoDTOS = crudService.getAll(name);

        byte[] logo;
        byte[] image;
        byte[] text;
        try {
            logo = logoService.get(name);
        } catch (IOException e) {
            throw new BezrukiyRuntimeException("Невозможно получить логотип");
        }
        try {
            image = imageService.get(name);
        } catch (IOException e) {
            throw new BezrukiyRuntimeException("Невозможно получить изображение");
        }
        try {
            text = textService.get(name);
        } catch (IOException e) {
            throw new BezrukiyRuntimeException("Невозможно получить описание");
        }

        List<byte[]> files = new ArrayList<>();
        files.add(logo);
        files.add(image);
        files.add(text);


        infoDTOS.forEach(x-> setFiles(x, files));
        return infoDTOS;
    }

    @Override
    public void writeFile(FileStorageDTO fileStorageDTO, MultipartFile newFile) {
        Objects.requireNonNull(fileStorageDTO, "Невозможно загрузить файл, т.к. данные не были загружены");
        Objects.requireNonNull(fileStorageDTO, "Невозможно загрузить файл, т.к. файл не был загружен");

        fileStorageDTO = fileStorageMapper.mapToEntityWithFile(newFile, fileStorageDTO);
        crudService.save(fileStorageDTO);
        String extension = getExtension(newFile.getOriginalFilename());

        if (isLogo(extension)) {
            try {
                logoService.save(newFile, fileStorageDTO.name());
            } catch (IOException e) {
                throw new BezrukiyRuntimeException(String.format("Ошибка сохранения логотипа %s", fileStorageDTO.name()));
            }
        }

        if (isImage(extension)) {
            try {
                imageService.save(newFile, fileStorageDTO.name());
            } catch (IOException e) {
                throw new BezrukiyRuntimeException(String.format("Ошибка сохранения изображения %s", fileStorageDTO.name()));
            }
        }

        if (isText(extension)) {
            try {
                textService.save(newFile, fileStorageDTO.name());
            } catch (IOException e) {
                throw new BezrukiyRuntimeException(String.format("Ошибка сохранения текста %s", fileStorageDTO.name()));
            }
        }
    }

    @Override
    public void updateFile(FileStorageUpdateDTO updateDTO, MultipartFile updateFile) {
        Objects.requireNonNull(updateDTO, "Невозможно обновить файл, т.к. новые данные не были получены");
        Objects.requireNonNull(updateFile, "Невозможно обновить фалй, т.к. файл не был отправлен");

        FileStorageDTO fileStorageDTO = fileStorageMapper.mapFromUpdateToDTO(updateFile, updateDTO);
        updateDTO = fileStorageMapper.mapToUpdateDto(fileStorageDTO, updateDTO.oldName());

        crudService.update(updateDTO);
        String extension = getExtension(updateFile.getOriginalFilename());
        String oldName = updateDTO.oldName();
        String newName = updateDTO.newData().name();

        if (isLogo(extension)) {
            try {
                logoService.update(updateFile, oldName, newName);
            } catch (IOException e) {
                throw new BezrukiyRuntimeException(String.format("Ошибка обновления логотипа %s", oldName));
            }
        }

        if (isImage(extension)) {
            try {
                imageService.update(updateFile, oldName, newName);
            } catch (IOException e) {
                throw new BezrukiyRuntimeException(String.format("Ошибка обновления изображения %s", oldName));
            }
        }

        if (isText(extension)) {
            try {
                textService.update(updateFile, oldName, newName);
            } catch (IOException e) {
                throw new BezrukiyRuntimeException(String.format("Ошибка обновления текста %s", oldName));
            }
        }
    }

    @Override
    public String deleteFile(String name) {
        Objects.requireNonNull(name, "Название файла(-ов) не были введены");

        try {
            logoService.delete(name);
        } catch (IOException e) {
            throw new BezrukiyRuntimeException("Невозможно удалить логотип");
        }

        try {
            imageService.delete(name);
        } catch (IOException e) {
            throw new BezrukiyRuntimeException("Невозможно удалить изображения");
        }

        try {
            textService.delete(name);
        } catch (IOException e) {
            throw new BezrukiyRuntimeException("Невозможно удалить текст");
        }

        return crudService.delete(name);
    }

    private void setFiles(FileStorageInfoDTO info, List<byte[]> files) {
        if (isLogo(info.type())) {
            info = new FileStorageInfoDTO(info.name(), info.type(), files.get(0));
        }

        if (isImage(info.type())) {
            info = new FileStorageInfoDTO(info.name(), info.type(), files.get(1));
        }

        if (isText(info.type())) {
            info = new FileStorageInfoDTO(info.name(), info.type(), files.get(2));
        }

//        return info;
    }
}