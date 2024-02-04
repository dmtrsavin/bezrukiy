package ru.savin.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.bezrukiy.shared.service.CrudService;
import ru.savin.core.dto.category.CategoryDTO;
import ru.savin.core.dto.category.CategoryUpdateDTO;
import ru.savin.core.entity.Category;
import ru.savin.core.mapper.CategoryMapper;
import ru.savin.core.repository.CategoryRepository;

import java.util.Objects;

/**
 * CRUD для категорий.
 */
@Service
@RequiredArgsConstructor
public class CategoryCrudServiceImpl implements CrudService<CategoryDTO, CategoryUpdateDTO> {
    private static final String ENTITY_NOT_FOUND = "Категория по названию %s не найдена";
    private static final String NAME_NULL = "Получить информации о категории невозможно, т.к. название категории равна null";

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO get(String name) {
        Objects.requireNonNull(name, NAME_NULL);

        Category category = categoryRepository.getCategoryByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        return categoryMapper.mapToDto(category);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Objects.requireNonNull(categoryDTO, "Создание новой категории невозможно, т.к. сущность равна null");

        Category category = categoryMapper.mapToEntity(categoryDTO);
        category = categoryRepository.save(category);

        return categoryMapper.mapToDto(category);
    }

    @Override
    public CategoryDTO update(CategoryUpdateDTO updateDTO) {
        Objects.requireNonNull(updateDTO, "Обновление категории невозможно, т.к. сущность равна null");
        Objects.requireNonNull(updateDTO.oldName(), NAME_NULL);

        Category categoryOld = categoryRepository.getCategoryByName(updateDTO.oldName())
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, updateDTO.oldName())));
        categoryMapper.update(updateDTO.updateData(), categoryOld);

        Category categoryUpdated = categoryRepository.save(categoryOld);
        return categoryMapper.mapToDto(categoryUpdated);
    }

    @Override
    public String delete(String name) {
        Objects.requireNonNull(name, NAME_NULL);

        Category category = categoryRepository.getCategoryByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        categoryRepository.delete(category);

        return String.format("Категория %s удалена", name);
    }
}
