package ru.savin.files.service;

import ru.savin.bezrukiy.shared.service.CrudService;
import ru.savin.files.dto.FileStorageDTO;
import ru.savin.files.dto.FileStorageInfoDTO;
import ru.savin.files.dto.FileStorageUpdateDTO;

import java.util.List;

public interface FileStorageCrud extends CrudService<FileStorageDTO, FileStorageUpdateDTO> {

    List<FileStorageInfoDTO> getAll(String name);
    void saveAll(FileStorageDTO dto, int sizeCountFiles);
    void updateAll(FileStorageDTO dto, int sizeCountFiles);
}
