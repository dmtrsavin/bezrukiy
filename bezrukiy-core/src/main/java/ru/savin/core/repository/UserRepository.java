package ru.savin.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.savin.core.entity.User;

/**
 * Репозиторий пользователя.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
