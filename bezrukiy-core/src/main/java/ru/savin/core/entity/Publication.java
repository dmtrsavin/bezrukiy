package ru.savin.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.savin.bezrukiy.shared.entity.GeneralEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Публикация.
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "publications", schema = "dev")
@AllArgsConstructor
@NoArgsConstructor
public class Publication extends GeneralEntity {

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}