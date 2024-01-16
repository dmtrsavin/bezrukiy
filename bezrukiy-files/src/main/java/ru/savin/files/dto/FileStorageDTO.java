package ru.savin.files.dto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;

/**
 * ДТО для файла.
 *
 * @param name название файла.
 * @param fileType тип файла.
 * @param createDttm дата создания файла.
 */
public record FileStorageDTO(String name, String fileType, OffsetDateTime createDttm,
                             OffsetDateTime endDttm, long externalId) {
}
