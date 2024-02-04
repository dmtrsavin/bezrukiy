package ru.savin.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.savin.core.entity.Publication;

import java.util.Optional;

/**
 * Репозиторий публикаций.
 */
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    /**
     * Получение информации о публикации.
     *
     * @param name название публикации.
     * @return {@link Publication}
     */
    Optional<Publication> getPublicationByName(@Param("name") String name);
}
