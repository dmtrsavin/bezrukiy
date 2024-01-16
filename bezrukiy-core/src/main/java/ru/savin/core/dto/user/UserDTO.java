package ru.savin.core.dto.user;

import java.time.OffsetDateTime;

/**
 * ДТО для пользователей.
 *
 * @param name название.
 * @param email эл. почта.
 * @param password пароль.
 * @param createDttm дата создания пользователя.
 */
public record UserDTO(String name, String email, String password, OffsetDateTime createDttm) {
}
