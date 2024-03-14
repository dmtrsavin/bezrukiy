package ru.savin.files.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;

/**
 * ДТО для файла.
 *
 * @param name название файла.
 * @param createDttm дата создания файла.
 */
public record FileStorageDTO(String name,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createDttm,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDttm,
                             long externalId, MultipartFile file) {
}
