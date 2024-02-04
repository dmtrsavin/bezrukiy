package ru.savin.core.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.core.dto.category.CategoryDTO;
import ru.savin.core.dto.category.CategoryUpdateDTO;
import ru.savin.core.entity.Category;
import ru.savin.core.mapper.CategoryMapper;
import ru.savin.core.repository.CategoryRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

@SpringJUnitConfig
class CategoryCrudServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryCrudServiceImpl crudService;

    @Test
    void getCategory_requestIsNull_nullPointerException() {
        //then
        Assertions.assertThrows(NullPointerException.class, () -> crudService.get(null));
    }

    @Test
    void getCategory_notFoundCategory_bezrukiyRuntimeException() {
        //given
        String name = "name";

        //when
        Mockito.when(categoryRepository.getCategoryByName(name))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.get(name));
    }

    @Test
    void getCategory_requestIsNotNull_success() {
        //given
        String name = "name";
        Category category = generateCategory();
        CategoryDTO expected = generateCategoryDTO();

        //when
        Mockito.when(categoryRepository.getCategoryByName(name))
                        .thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.mapToDto(category))
                        .thenReturn(expected);

        //then
        Assertions.assertEquals(expected, crudService.get(name));
    }

    @Test
    void saveCategory_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.save(null));
    }

    @Test
    void saveCategory_requestIsNotNull_success() {
        //given
        Category entity = generateCategory();
        CategoryDTO expected = generateCategoryDTO();

        //when
        Mockito.when(categoryMapper.mapToEntity(expected)).thenReturn(entity);
        Mockito.when(categoryRepository.save(entity)).thenReturn(entity);
        Mockito.when(categoryMapper.mapToDto(entity)).thenReturn(expected);

        //then
        Assertions.assertEquals(expected, crudService.save(expected));
    }

    @Test
    void updateCategory_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.update(null));
    }

    @Test
    void updateCategory_nameIsNull_nullPointerException() {
        //given
        CategoryDTO data = new CategoryDTO("name", OffsetDateTime.now());
        CategoryUpdateDTO updateDTO = new CategoryUpdateDTO(null, data);

        //then
        Assertions.assertThrows(NullPointerException.class, () -> crudService.update(updateDTO));
    }

    @Test
    void updateCategory_notFoundCategory_bezrukiyRuntimeException() {
        //given
        CategoryDTO data = new CategoryDTO("name", OffsetDateTime.now());
        CategoryUpdateDTO updateDTO = new CategoryUpdateDTO("name", data);

        //when
        Mockito.when(categoryRepository.getCategoryByName(updateDTO.oldName()))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.update(updateDTO));
    }

    @Test
    void updateCategory_requestIsNotNull_success() {
        //given
        CategoryDTO categoryDTO = new CategoryDTO("name", OffsetDateTime.now());
        CategoryUpdateDTO updateDTO = new CategoryUpdateDTO("name", categoryDTO);
        Category entity = generateCategory();

        //when
        Mockito.when(categoryRepository.getCategoryByName(updateDTO.oldName()))
                .thenReturn(Optional.of(entity));
        Mockito.when(categoryRepository.save(entity))
                .thenReturn(entity);
        Mockito.when(categoryMapper.mapToDto(entity))
                .thenReturn(categoryDTO);

        //then
        crudService.update(updateDTO);
        Mockito.verify(categoryMapper, Mockito.times(1)).update(categoryDTO, entity);
        Assertions.assertEquals(categoryDTO, crudService.update(updateDTO));
    }

    @Test
    void deleteCategory_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.delete(null));
    }

    @Test
    void deleteCategory_notFoundCategory_bezrukiyRuntimeException() {
        //given
        String name = "name";

        //when
        Mockito.when(categoryRepository.getCategoryByName(name))
                        .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.delete(name));
    }

    @Test
    void deleteCategory_requestIsNotNull_success() {
        //given
        String name = "name";
        Category entity = generateCategory();

        //when
        Mockito.when(categoryRepository.getCategoryByName(name))
                .thenReturn(Optional.of(entity));

        //then
        String result = crudService.delete(name);
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(entity);
        Assertions.assertEquals(String.format("Категория %s удалена", name), result);
    }

    private Category generateCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("name");
        category.setCreateDttm(OffsetDateTime.now());

        return category;
    }

    private CategoryDTO generateCategoryDTO() {
        return new CategoryDTO("name", OffsetDateTime.now());
    }
}
