package ru.savin.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.savin.bezrukiy.shared.entity.GeneralFileStorageEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "file_storages", schema = "dev")
@NoArgsConstructor
@AllArgsConstructor
public class FileStorage extends GeneralFileStorageEntity {

    @Column(name = "modify_Dttm")
    private OffsetDateTime modifyDttm;

    @OneToOne
    private Category category;

    @ManyToOne
    private Publication publication;

    @OneToOne
    private User user;
}

