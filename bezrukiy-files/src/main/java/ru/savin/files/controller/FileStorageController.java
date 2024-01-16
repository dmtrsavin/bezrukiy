package ru.savin.files.controller;

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
import ru.savin.files.dto.FileStorageDTO;
import ru.savin.files.dto.FileStorageUpdateDTO;
import ru.savin.files.entity.FileStorage;

/**
 * Контроллер файлов.
 */
@RestController
@RequestMapping("/file-storage")
@RequiredArgsConstructor
public class FileStorageController {
    private final CrudService<FileStorageDTO, FileStorageUpdateDTO> crudService;

    @GetMapping("/{name}")
    public FileStorageDTO get(@PathVariable("name") String name) {
        return crudService.get(name);
    }

    @PostMapping
    public FileStorageDTO save(@RequestBody FileStorageDTO fileStorageDTO) {
        return crudService.save(fileStorageDTO);
    }

    @PutMapping
    public FileStorageDTO update(@RequestBody FileStorageUpdateDTO updateDTO) {
        return crudService.update(updateDTO);
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name) {
        return crudService.delete(name);
    }
}
