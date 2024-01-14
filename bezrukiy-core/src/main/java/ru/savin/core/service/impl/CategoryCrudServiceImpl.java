package ru.savin.core.service.impl;

import lombok.RequiredArgsConstructor;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.bezrukiy.shared.service.CrudService;
import ru.savin.core.dto.category.CategoryDTO;
import ru.savin.core.entity.Category;
import ru.savin.core.mapper.CategoryMapper;
import ru.savin.core.repository.CategoryRepository;

import java.util.Objects;

/**
 * CRUD для категорий.
 */
@RequiredArgsConstructor
public class CategoryCrudServiceImpl implements CrudService<CategoryDTO, Category> {
    private static final String ENTITY_NOT_FOUND = "Категория по названию %s не найдена";

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO get(String name) {
        Objects.requireNonNull(name, "Получить информации о категории невозможно, т.к. название категории равна null");

        return categoryRepository.getByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
    }

    @Override
    public CategoryDTO save(Category category) {
        Objects.requireNonNull(category, "Создание новой категории невозможно, т.к. сущность равна null");

        category = categoryRepository.save(category);
        return categoryMapper.mapToDto(category);
    }

    @Override
    public CategoryDTO update(Category category) {
        Objects.requireNonNull(category, "Обновление категории невозможно, т.к. сущность равна null");

        category = categoryRepository.save(category);
        return categoryMapper.mapToDto(category);
    }

    @Override
    public String delete(String name) {
        Objects.requireNonNull(name, "Название категории null");

        Category category = categoryRepository.getByNameCategory(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        categoryRepository.delete(category);

        return String.format("Категория %s удалена", name);
    }
}
