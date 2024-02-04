package ru.savin.core.dto.user;

/**
 * ДТО для обновления пользователей.
 *
 * @param oldName нынешний никнейм.
 * @param newData новые данные.
 */
public record UserUpdateDTO(String oldName, UserDTO newData) {
}
