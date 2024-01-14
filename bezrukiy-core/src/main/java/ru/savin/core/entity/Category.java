package ru.savin.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.savin.bezrukiy.shared.entity.GeneralEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Категория.
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "categories", schema = "dev")
@AllArgsConstructor
@NoArgsConstructor
public class Category extends GeneralEntity {

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "category")
    private List<Publication> publications;
}


