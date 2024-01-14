package ru.savin.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.savin.bezrukiy.shared.entity.GeneralEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Пользователь.
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "users", schema = "dev")
@AllArgsConstructor
@NoArgsConstructor
public class User extends GeneralEntity {

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Publication> publications;
}
