package ru.savin.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.savin.core.dto.user.UserDTO;
import ru.savin.core.entity.User;

/**
 * Маппер пользователей.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Сконвертировать {@link User} в {@link UserDTO}.
     *
     * @param user сущность.
     * @return {@link UserDTO}
     */
    UserDTO mapToDto(User user);

    /**
     * Сконвертировать {@link UserDTO} в {@link User}.
     *
     * @param userDTO ДТО.
     * @return {@link User}
     */
    User mapToEntity(UserDTO userDTO);

    /**
     * Обновление.
     *
     * @param userDTO ДТО с новыми данными.
     * @param user сущность
     */
    void update(UserDTO userDTO, @MappingTarget User user);
}
