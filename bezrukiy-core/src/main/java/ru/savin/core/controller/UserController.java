package ru.savin.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savin.bezrukiy.shared.service.CrudService;
import ru.savin.core.dto.user.UserDTO;
import ru.savin.core.dto.user.UserUpdateDTO;

/**
 * Котроллер пользователей.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final CrudService<UserDTO, UserUpdateDTO> crudService;

    @GetMapping("/{name}")
    public UserDTO get(@PathVariable("name") String name) {
        return crudService.get(name);
    }

    @PostMapping
    public UserDTO save(@RequestBody UserDTO userDTO) {
        return crudService.save(userDTO);
    }

    @PutMapping
    public UserDTO update(@RequestBody UserUpdateDTO updateDTO) {
        return crudService.update(updateDTO);
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name) {
        return crudService.delete(name);
    }
 }
