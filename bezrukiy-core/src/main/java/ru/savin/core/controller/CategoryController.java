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
import ru.savin.core.dto.category.CategoryDTO;
import ru.savin.core.dto.category.CategoryUpdateDTO;

/**
 * Контроллер категорий.
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CrudService<CategoryDTO, CategoryUpdateDTO> crudService;

    @GetMapping("/{name}")
    public CategoryDTO getByName(@PathVariable("name") String name) {
        return crudService.get(name);
    }

    @PostMapping
    public CategoryDTO save(@RequestBody CategoryDTO categoryDTO) {
        return crudService.save(categoryDTO);
    }

    @PutMapping
    public CategoryDTO update(@RequestBody CategoryUpdateDTO updateDTO) {
        return crudService.update(updateDTO);
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name) {
        return crudService.delete(name);
    }
}