package ru.savin.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.savin.core.entity.User;

import java.util.Optional;

/**
 * Репозиторий пользователя.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Получение информации о пользователе.
     *
     * @param userName никнейм пользователя.
     * @return {@link Optional}
     */
    Optional<User> getUserByName(@Param("userName") String userName);
}
