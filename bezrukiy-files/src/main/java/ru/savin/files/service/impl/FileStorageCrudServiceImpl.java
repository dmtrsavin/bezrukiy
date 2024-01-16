package ru.savin.files.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.bezrukiy.shared.service.CrudService;
import ru.savin.files.dto.FileStorageDTO;
import ru.savin.files.dto.FileStorageUpdateDTO;
import ru.savin.files.entity.FileStorage;
import ru.savin.files.mapper.FileStorageMapper;
import ru.savin.files.repository.FileStorageRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileStorageCrudServiceImpl implements CrudService<FileStorageDTO, FileStorageUpdateDTO> {
    private static final String ENTITY_NOT_FOUND = "Файл по названию %s не найден";
    private static final String NAME_NULL = "Получить информации о файле невозможно, т.к. название файла равно null";

    private final FileStorageRepository fileStorageRepository;
    private final FileStorageMapper fileStorageMapper;

    @Override
    public FileStorageDTO get(String name) {
        Objects.requireNonNull(name, NAME_NULL);

        FileStorage fileStorage = fileStorageRepository.getFileStorageByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        return fileStorageMapper.mapToDTO(fileStorage);
    }

    @Override
    public FileStorageDTO save(FileStorageDTO fileStorageDTO) {
        Objects.requireNonNull(fileStorageDTO, "Невозможно сохранить файл, так как объект null");

        FileStorage fileStorage = fileStorageMapper.mapToEntity(fileStorageDTO);
        fileStorage = fileStorageRepository.save(fileStorage);

        return fileStorageMapper.mapToDTO(fileStorage);
    }

    @Override
    public FileStorageDTO update(FileStorageUpdateDTO updateDTO) {
        Objects.requireNonNull(updateDTO, "Невозможно обновиить файл, так как объект null");
        Objects.requireNonNull(updateDTO.oldName(), NAME_NULL);

        FileStorage fileStorageOld = fileStorageRepository.getFileStorageByName(updateDTO.oldName())
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, updateDTO.oldName())));
        fileStorageMapper.update(updateDTO.newData(), fileStorageOld);

        FileStorage fileStorageUpdated = fileStorageRepository.save(fileStorageOld);
        return fileStorageMapper.mapToDTO(fileStorageUpdated);
    }

    @Override
    public String delete(String name) {
        Objects.requireNonNull(name, NAME_NULL);

        FileStorage fileStorage = fileStorageRepository.getFileStorageByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        fileStorageRepository.delete(fileStorage);

        return String.format("Файл %s удален", name);
    }
}
