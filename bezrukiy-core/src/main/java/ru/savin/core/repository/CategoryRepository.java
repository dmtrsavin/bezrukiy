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
     * Получение информации о конкретной категории по названию.
     *
     * @param nameCategory название категории.
     * @return {@link Category}
     */
    Optional<Category> getCategoryByName(@Param("nameCategory") String nameCategory);
}
