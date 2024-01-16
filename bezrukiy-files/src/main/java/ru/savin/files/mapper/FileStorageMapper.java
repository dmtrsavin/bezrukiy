package ru.savin.files.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.savin.files.dto.FileStorageDTO;
import ru.savin.files.entity.FileStorage;

/**
 * Маппер файла.
 */
@Mapper(componentModel = "spring")
public interface FileStorageMapper {

    /**
     * Сконвертировать {@link FileStorage} в {@link FileStorageDTO}.
     *
     * @param fileStorage сущность.
     * @return {@link FileStorageDTO}
     */
    FileStorageDTO mapToDTO(FileStorage fileStorage);

    /**
     * Сконвертировать {@link FileStorageDTO} в {@link FileStorage}.
     *
     * @param fileStorageDTO ДТО.
     * @return {@link FileStorage}
     */
    @InheritInverseConfiguration(name = "mapToDTO")
    FileStorage mapToEntity(FileStorageDTO fileStorageDTO);

    /**
     * Обновление.
     *
     * @param update сущность с новыми данными.
     * @param fileStorage сущность.
     */
    void update(FileStorageDTO update, @MappingTarget FileStorage fileStorage);
}
