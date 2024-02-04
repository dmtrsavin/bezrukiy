package ru.savin.core.dto.publication;

/**
 * ДТО для обновления публикаций.
 *
 * @param oldName нынешнее название.
 * @param newData новые данные.
 */
public record PublicationUpdateDTO(String oldName, PublicationDTO newData) {
}
