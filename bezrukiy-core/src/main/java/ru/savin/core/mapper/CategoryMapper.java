package ru.savin.core.mapper;

import org.mapstruct.Mapper;
import ru.savin.core.dto.category.CategoryDTO;
import ru.savin.core.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO mapToDto(Category category);
}
