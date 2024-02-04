package ru.savin.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.savin.core.dto.publication.PublicationDTO;
import ru.savin.core.entity.Publication;

/**
 * Маппер публикаций.
 */
@Mapper(componentModel = "spring")
public interface PublicationMapper {

    /**
     * Сконвертировать {@link Publication} в {@link PublicationDTO}
     *
     * @param publication сущность.
     * @return {@link PublicationDTO}
     */
    PublicationDTO mapToDto(Publication publication);

    /**
     * Сконверитровать {@link PublicationDTO} в {@link Publication}
     *
     * @param publicationDTO ДТО.
     * @return {@link Publication}
     */
    Publication mapToEntity(PublicationDTO publicationDTO);

    /**
     * Обновление.
     *
     * @param update ДТО с новыми данными.
     * @param publication сущность.
     */
    void update(PublicationDTO update, @MappingTarget Publication publication);
}
