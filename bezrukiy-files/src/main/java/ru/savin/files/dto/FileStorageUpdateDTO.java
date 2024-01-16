package ru.savin.files.dto;

/**
 * ДТО для обновления файла.
 *
 * @param oldName нынешнее название.
 * @param newData новые данные.
 */
public record FileStorageUpdateDTO(String oldName, FileStorageDTO newData) {
}
