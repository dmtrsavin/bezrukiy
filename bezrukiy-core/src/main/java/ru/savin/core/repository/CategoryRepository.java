package ru.savin.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.savin.core.dto.category.CategoryDTO;
import ru.savin.core.entity.Category;

import java.util.Optional;

/**
 * Репозиторий категории.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Получение информации о конкретной категории по имени.
     *
     * @param nameCategory - Названние категории.
     * @return {@link CategoryDTO}
     */
    @Query("select new ru.savin.core.dto.category.CategoryDTO(c.name, c.createDttm, c.endDttm) " +
            "from Category c where c.name = :nameCategory")
    Optional<CategoryDTO> getByName(@Param("nameCategory") String nameCategory);

    /**
     * Получение информации о конрктеной категории по имени.
     *
     * @param nameCategory - Название категории.
     * @return {@link Category}
     */
    @Query("select c from Category c where c.name = :nameCategory")
    Optional<Category> getByNameCategory(@Param("nameCategory") String nameCategory);
}
