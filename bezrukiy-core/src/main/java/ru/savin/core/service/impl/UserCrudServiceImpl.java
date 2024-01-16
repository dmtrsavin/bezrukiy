package ru.savin.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.bezrukiy.shared.service.CrudService;
import ru.savin.core.dto.user.UserDTO;
import ru.savin.core.dto.user.UserUpdateDTO;
import ru.savin.core.entity.User;
import ru.savin.core.mapper.UserMapper;
import ru.savin.core.repository.UserRepository;

import java.util.Objects;

/**
 * CRUD для пользователей.
 */
@Service
@RequiredArgsConstructor
public class UserCrudServiceImpl implements CrudService<UserDTO, UserUpdateDTO> {
    private static final String ENTITY_NOT_FOUND = "Пользователь с никнеймом %s не найден";
    private static final String NAME_NULL = "Получить информации о пользователе невозможно, т.к. название публикации равна null";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO get(String name) {
        Objects.requireNonNull(name, NAME_NULL);

        User user = userRepository.getUserByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        Objects.requireNonNull(userDTO, "Создание пользователя невозможно. Тело запроса null");

        User user = userMapper.mapToEntity(userDTO);
        user = userRepository.save(user);

        return userMapper.mapToDto(user);
    }

    @Override
    public UserDTO update(UserUpdateDTO updateDTO) {
        Objects.requireNonNull(updateDTO, "Обновление пользователя невозможно. Тело запроса null");
        Objects.requireNonNull(updateDTO.oldName(), NAME_NULL);

        User userOld = userRepository.getUserByName(updateDTO.oldName())
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, updateDTO.oldName())));
        userMapper.update(updateDTO.newData(), userOld);

        User userUpdated = userRepository.save(userOld);
        return userMapper.mapToDto(userUpdated);
    }

    @Override
    public String delete(String name) {
        Objects.requireNonNull(name, NAME_NULL);

        User user = userRepository.getUserByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        userRepository.delete(user);

        return String.format("Пользователь %s удален", name);
    }
}
