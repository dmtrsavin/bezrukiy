package ru.savin.files.service.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.files.dto.FileStorageDTO;
import ru.savin.files.dto.FileStorageUpdateDTO;
import ru.savin.files.entity.FileStorage;
import ru.savin.files.entity.enums.FileType;
import ru.savin.files.mapper.FileStorageMapper;
import ru.savin.files.repository.FileStorageRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringJUnitConfig
class FileStorageCrudServiceImplTest {
    @Mock
    private FileStorageRepository fileStorageRepository;
    @Mock
    private FileStorageMapper fileStorageMapper;

    @InjectMocks
    private FileStorageCrudServiceImpl crudService;

    @Test
    void getFileStorage_requestIsNull_nullPointerException() {
        //then
        Assertions.assertThrows(NullPointerException.class, () -> crudService.get(null));
    }

    @Test
    void getFileStorage_notFoundCategory_bezrukiyRuntimeException() {
        //given
        String name = "name";

        //when
        Mockito.when(fileStorageRepository.getFileStorageByName(name))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.get(name));
    }

    @Test
    void getFileStorage_requestIsNotNull_success() {
        //given
        String name = "name";
        FileStorage entity = generateFileStorage();
        FileStorageDTO expected = generateFileStorageDTO();

        //when
        Mockito.when(fileStorageRepository.getFileStorageByName(name))
                .thenReturn(Optional.of(entity));
        Mockito.when(fileStorageMapper.mapToDTO(entity))
                .thenReturn(expected);

        //then
        Assertions.assertEquals(expected, crudService.get(name));
    }

    @Test
    void saveFileStorage_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.save(null));
    }

    @Test
    void saveFileStorage_requestIsNotNull_success() {
        //given
        FileStorage entity = generateFileStorage();
        FileStorageDTO expected = generateFileStorageDTO();

        //when
        Mockito.when(fileStorageMapper.mapToEntity(expected)).thenReturn(entity);
        Mockito.when(fileStorageRepository.save(entity)).thenReturn(entity);
        Mockito.when(fileStorageMapper.mapToDTO(entity)).thenReturn(expected);

        //then
        Assertions.assertEquals(expected, crudService.save(expected));
    }

    @Test
    void updateFileStorage_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.update(null));
    }

    @Test
    void updateFileStorage_nameIsNull_nullPointerException() {
        //given
        FileStorageDTO data = generateFileStorageDTO();
        FileStorageUpdateDTO updateDTO = new FileStorageUpdateDTO(null, data);

        //then
        Assertions.assertThrows(NullPointerException.class, () -> crudService.update(updateDTO));
    }

    @Test
    void updateFileStorage_notFoundCategory_bezrukiyRuntimeException() {
        //given
        FileStorageDTO data = generateFileStorageDTO();
        FileStorageUpdateDTO updateDTO = new FileStorageUpdateDTO("name", data);

        //when
        Mockito.when(fileStorageRepository.getFileStorageByName(updateDTO.oldName()))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.update(updateDTO));
    }

    @Test
    void updateFileStorage_requestIsNotNull_success() {
        //given
        FileStorageDTO data = generateFileStorageDTO();
        FileStorageUpdateDTO updateDTO = new FileStorageUpdateDTO("name", data);
        FileStorage entity = generateFileStorage();

        //when
        Mockito.when(fileStorageRepository.getFileStorageByName(updateDTO.oldName()))
                .thenReturn(Optional.of(entity));
        Mockito.when(fileStorageRepository.save(entity))
                .thenReturn(entity);
        Mockito.when(fileStorageMapper.mapToDTO(entity))
                .thenReturn(data);

        //then
        crudService.update(updateDTO);
        Mockito.verify(fileStorageMapper, Mockito.times(1)).update(data, entity);
        Assertions.assertEquals(data, crudService.update(updateDTO));
    }

    @Test
    void deleteFileStorage_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.delete(null));
    }

    @Test
    void deleteFileStorage_notFoundCategory_bezrukiyRuntimeException() {
        //given
        String name = "name";

        //when
        Mockito.when(fileStorageRepository.getFileStorageByName(name))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.delete(name));
    }

    @Test
    void deleteFileStorage_requestIsNotNull_success() {
        //given
        String name = "name";
        FileStorage entity = generateFileStorage();
        List<FileStorage> fileStorages = new ArrayList<>();
        fileStorages.add(entity);

        //when
        Mockito.when(fileStorageRepository.getFileStoragesByName(name))
                .thenReturn(fileStorages);

        //then
        String result = crudService.delete(name);
        Mockito.verify(fileStorageRepository, Mockito.times(1)).deleteAll(fileStorages);
        Assertions.assertEquals(String.format("Файлы удалены", name), result);
    }

    private FileStorage generateFileStorage() {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setFileId(UUID.randomUUID());
        fileStorage.setName("name");
        fileStorage.setCreateDttm(OffsetDateTime.now());

        return fileStorage;
    }

    private FileStorageDTO generateFileStorageDTO() {
        return new FileStorageDTO(
                "name", OffsetDateTime.now(), OffsetDateTime.now(), 11L, null);
    }

}