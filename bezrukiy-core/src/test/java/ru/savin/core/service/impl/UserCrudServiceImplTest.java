package ru.savin.core.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.core.dto.user.UserDTO;
import ru.savin.core.dto.user.UserUpdateDTO;
import ru.savin.core.entity.User;
import ru.savin.core.mapper.UserMapper;
import ru.savin.core.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

@SpringJUnitConfig
class UserCrudServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserCrudServiceImpl crudService;

    @Test
    void getUser_requestIsNull_nullPointerException() {
        //then
        Assertions.assertThrows(NullPointerException.class, () -> crudService.get(null));
    }

    @Test
    void getUser_notFoundCategory_bezrukiyRuntimeException() {
        //given
        String name = "name";

        //when
        Mockito.when(userRepository.getUserByName(name))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.get(name));
    }

    @Test
    void getUser_requestIsNotNull_success() {
        //given
        String name = "name";
        User entity = generateUser();
        UserDTO expected = generateUserDTO();

        //when
        Mockito.when(userRepository.getUserByName(name))
                .thenReturn(Optional.of(entity));
        Mockito.when(userMapper.mapToDto(entity))
                .thenReturn(expected);

        //then
        Assertions.assertEquals(expected, crudService.get(name));
    }

    @Test
    void saveUser_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.save(null));
    }

    @Test
    void saveUser_requestIsNotNull_success() {
        //given
        User entity = generateUser();
        UserDTO expected = generateUserDTO();

        //when
        Mockito.when(userMapper.mapToEntity(expected)).thenReturn(entity);
        Mockito.when(userRepository.save(entity)).thenReturn(entity);
        Mockito.when(userMapper.mapToDto(entity)).thenReturn(expected);

        //then
        Assertions.assertEquals(expected, crudService.save(expected));
    }

    @Test
    void updateUser_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.update(null));
    }

    @Test
    void updateUser_nameIsNull_nullPointerException() {
        //given
        UserDTO data = generateUserDTO();
        UserUpdateDTO updateDTO = new UserUpdateDTO(null, data);

        //then
        Assertions.assertThrows(NullPointerException.class, () -> crudService.update(updateDTO));
    }

    @Test
    void updateUser_notFoundCategory_bezrukiyRuntimeException() {
        //given
        UserDTO data = generateUserDTO();
        UserUpdateDTO updateDTO = new UserUpdateDTO("name", data);

        //when
        Mockito.when(userRepository.getUserByName(updateDTO.oldName()))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.update(updateDTO));
    }

    @Test
    void updateUser_requestIsNotNull_success() {
        //given
        UserDTO data = generateUserDTO();
        UserUpdateDTO updateDTO = new UserUpdateDTO("name", data);
        User entity = generateUser();

        //when
        Mockito.when(userRepository.getUserByName(updateDTO.oldName()))
                .thenReturn(Optional.of(entity));
        Mockito.when(userRepository.save(entity))
                .thenReturn(entity);
        Mockito.when(userMapper.mapToDto(entity))
                .thenReturn(data);

        //then
        crudService.update(updateDTO);
        Mockito.verify(userMapper, Mockito.times(1)).update(data, entity);
        Assertions.assertEquals(data, crudService.update(updateDTO));
    }

    @Test
    void deleteUser_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.delete(null));
    }

    @Test
    void deleteUser_notFoundCategory_bezrukiyRuntimeException() {
        //given
        String name = "name";

        //when
        Mockito.when(userRepository.getUserByName(name))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.delete(name));
    }

    @Test
    void deleteUser_requestIsNotNull_success() {
        //given
        String name = "name";
        User entity = generateUser();

        //when
        Mockito.when(userRepository.getUserByName(name))
                .thenReturn(Optional.of(entity));

        //then
        String result = crudService.delete(name);
        Mockito.verify(userRepository, Mockito.times(1)).delete(entity);
        Assertions.assertEquals(String.format("Пользователь %s удален", name), result);
    }

    private User generateUser() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setCreateDttm(OffsetDateTime.now());

        return user;
    }

    private UserDTO generateUserDTO() {
        return new UserDTO(
                "name", "email", "password", OffsetDateTime.now());
    }
}