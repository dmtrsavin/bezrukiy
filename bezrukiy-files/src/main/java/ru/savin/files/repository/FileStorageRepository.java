package ru.savin.files.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.savin.files.dto.FileStorageDTO;
import ru.savin.files.entity.FileStorage;

import java.util.Optional;

/**
 * Репозиторий файлов.
 */
@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {

    /**
     * Получение информации о файле по названию.
     *
     * @param name название файла.
     * @return {@link FileStorageDTO}
     */
    Optional<FileStorage> getFileStorageByName(@Param("fileName") String name);
}
