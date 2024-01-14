package ru.savin.core.dto.publication;

import java.time.OffsetDateTime;

/**
 * ДТО для публикаций.
 *
 * @param name - Название.
 * @param createDttm - Дата создания.
 * @param endDttm - Дата окончания.
 * @param nameCategory - Название категории.
 * @param nameUser - Имя пользователя.
 */
public record PublicationDTO(String name, OffsetDateTime createDttm,
                            OffsetDateTime endDttm, String nameCategory,
                            String nameUser) {
}
