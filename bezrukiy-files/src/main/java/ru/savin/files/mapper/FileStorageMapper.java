package ru.savin.files.mapper;

import org.apache.commons.io.FilenameUtils;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.files.dto.FileStorageDTO;
import ru.savin.files.dto.FileStorageInfoDTO;
import ru.savin.files.dto.FileStorageUpdateDTO;
import ru.savin.files.entity.FileStorage;
import ru.savin.files.entity.enums.FileType;

import java.util.List;

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
    @Named(value = "mapToEntity")
    @InheritInverseConfiguration(name = "mapToDTO")
    FileStorage mapToEntity(FileStorageDTO fileStorageDTO);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "createDttm", source = "dto.createDttm")
    @Mapping(target = "endDttm", source = "dto.endDttm")
    @Mapping(target = "externalId", source = "dto.externalId")
    @Mapping(target = "file", source = "file")
    FileStorageDTO mapToEntityWithFile(MultipartFile file, FileStorageDTO dto);

    @IterableMapping(qualifiedByName = "mapToEntity")
    List<FileStorage> mapToEntities(List<FileStorageDTO> fileStorageDTOS);

    @Mapping(target = "name", source = "updateDTO.newData.name")
    @Mapping(target = "createDttm", source = "updateDTO.newData.createDttm")
    @Mapping(target = "endDttm", source = "updateDTO.newData.endDttm")
    @Mapping(target = "externalId", source = "updateDTO.newData.externalId")
    @Mapping(target = "file", source = "file")
    FileStorageDTO mapFromUpdateToDTO(MultipartFile file, FileStorageUpdateDTO updateDTO);

    @Mapping(source = "fileStorageDTO.name", target = "newData.name")
    @Mapping(source = "fileStorageDTO.createDttm", target = "newData.createDttm")
    @Mapping(source = "fileStorageDTO.endDttm", target = "newData.endDttm")
    @Mapping(source = "fileStorageDTO.externalId", target = "newData.externalId")
    @Mapping(source = "fileStorageDTO.file", target = "newData.file")
    FileStorageUpdateDTO mapToUpdateDto(FileStorageDTO fileStorageDTO, String oldName);

    FileStorageInfoDTO mapInfoDTO(String name, byte[] file);

    /**
     * Обновление.
     *
     * @param update сущность с новыми данными.
     * @param fileStorage сущность.
     */
    void update(FileStorageDTO update, @MappingTarget FileStorage fileStorage);

    @AfterMapping
    default void getExtension(FileStorageDTO fileStorageDTO, @MappingTarget FileStorage fileStorage) {
        String extension = FilenameUtils.getExtension(fileStorageDTO.file().getOriginalFilename());

        if (extension == null) {
            throw new BezrukiyRuntimeException("Расширение не найдено");
        }

        if (extension.equals(FileType.LOGO.getExtension())) {
            fileStorage.setFileType(FileType.LOGO);
        }

        if (extension.equals(FileType.TEXT.getExtension())) {
            fileStorage.setFileType(FileType.TEXT);
        }

        if (extension.equals(FileType.IMAGE.getExtension())) {
            fileStorage.setFileType(FileType.IMAGE);
        }
    }
}
