package ru.savin.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.savin.core.entity.Publication;

import java.util.Optional;

/**
 * Репозиторий публикаций.
 */
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query("select p from Publication p where p.name = :name")
    Optional<Publication> getByName(@Param("name") String name);
}
