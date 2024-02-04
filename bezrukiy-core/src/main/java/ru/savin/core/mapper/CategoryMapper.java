package ru.savin.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.savin.core.dto.category.CategoryDTO;
import ru.savin.core.entity.Category;

/**
 * Маппер категорий.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * Сконвертировать {@link Category} в {@link CategoryDTO}
     *
     * @param category сущность.
     * @return {@link CategoryDTO}
     */
    CategoryDTO mapToDto(Category category);

    /**
     * Сконвертировать {@link CategoryDTO} в {@link Category}
     *
     * @param categoryDTO ДТО.
     * @return {@link Category}
     */
    Category mapToEntity(CategoryDTO categoryDTO);

    /**
     * Обновление.
     *
     * @param update ДТО с новыми данными.
     * @param category сущность.
     */
    void update(CategoryDTO update, @MappingTarget Category category);
}
