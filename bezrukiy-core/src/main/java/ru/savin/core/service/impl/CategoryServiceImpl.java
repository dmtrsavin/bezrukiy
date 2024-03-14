package ru.savin.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.savin.bezrukiy.shared.service.CrudService;
import ru.savin.core.dto.category.CategoryDTO;
import ru.savin.core.dto.category.CategoryUpdateDTO;
import ru.savin.core.service.CategoryService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CrudService<CategoryDTO, CategoryUpdateDTO> crudService;

    @Override
    public void create(CategoryDTO categoryDTO) {
        Objects.requireNonNull(categoryDTO, "jsdfl");
        crudService.save(categoryDTO);
    }
}
