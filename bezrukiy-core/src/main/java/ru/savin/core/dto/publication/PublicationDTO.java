package ru.savin.core.dto.publication;

import java.time.OffsetDateTime;

/**
 * ДТО для публикаций.
 *
 * @param name название.
 * @param createDttm дата создания.
 * @param endDttm дата окончания.
 * @param nameCategory название категории.
 * @param nameUser имя пользователя.
 */
public record PublicationDTO(String name, OffsetDateTime createDttm,
                            OffsetDateTime endDttm, String nameCategory,
                            String nameUser) {
}
