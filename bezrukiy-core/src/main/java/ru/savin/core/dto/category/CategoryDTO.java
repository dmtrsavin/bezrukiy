package ru.savin.core.dto.category;

import java.time.OffsetDateTime;

/**
 * ДТО для категорий.
 *
 * @param name название.
 * @param createDttm дата создания.
 */
public record CategoryDTO(String name, OffsetDateTime createDttm) {
}
