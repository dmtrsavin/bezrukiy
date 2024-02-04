package ru.savin.files.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.savin.bezrukiy.shared.entity.GeneralFileStorageEntity;
import ru.savin.files.entity.enums.FileType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Файл.
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "file_storage", schema = "dev")
@AllArgsConstructor
@NoArgsConstructor
public class FileStorage extends GeneralFileStorageEntity {

    @Column(name = "type", nullable = false)
    private FileType fileType;

    @Column(name = "external_id", nullable = false)
    private Long externalId;
}

