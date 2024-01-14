package ru.savin.core.mapper;

import org.mapstruct.Mapper;
import ru.savin.core.dto.publication.PublicationDTO;
import ru.savin.core.entity.Publication;

@Mapper(componentModel = "spring")
public interface PublicationMapper {

    PublicationDTO mapToDto(Publication publication);

    Publication mapToEntity(PublicationDTO publicationDTO);
}
