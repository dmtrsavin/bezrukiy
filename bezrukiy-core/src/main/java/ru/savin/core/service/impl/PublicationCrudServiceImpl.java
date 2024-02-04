package ru.savin.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.bezrukiy.shared.service.CrudService;
import ru.savin.core.dto.publication.PublicationDTO;
import ru.savin.core.dto.publication.PublicationUpdateDTO;
import ru.savin.core.entity.Publication;
import ru.savin.core.mapper.PublicationMapper;
import ru.savin.core.repository.PublicationRepository;

import java.util.Objects;

/**
 * CRUD для публикаций.
 */
@Service
@RequiredArgsConstructor
public class PublicationCrudServiceImpl implements CrudService<PublicationDTO, PublicationUpdateDTO> {
    private static final String ENTITY_NOT_FOUND = "Публикация с названием %s не найдена";
    private static final String NAME_NULL = "Получить информации о публикации невозможно, т.к. название публикации равна null";

    private final PublicationMapper publicationMapper;
    private final PublicationRepository publicationRepository;

    @Override
    public PublicationDTO get(String name) {
        Objects.requireNonNull(name, NAME_NULL);

        Publication publication = publicationRepository.getPublicationByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        return publicationMapper.mapToDto(publication);
    }

    @Override
    public PublicationDTO save(PublicationDTO publicationDTO) {
        Objects.requireNonNull(publicationDTO, "Создание публикации невозможно. Тело запроса null");

        Publication publication = publicationMapper.mapToEntity(publicationDTO);
        publication = publicationRepository.save(publication);

        return publicationMapper.mapToDto(publication);
    }

    @Override
    public PublicationDTO update(PublicationUpdateDTO updateDTO) {
        Objects.requireNonNull(updateDTO, "Обновление публикации невозможно. Тело запроса null");
        Objects.requireNonNull(updateDTO.oldName(), NAME_NULL);

        Publication publicationOld = publicationRepository.getPublicationByName(updateDTO.oldName())
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, updateDTO.oldName())));
        publicationMapper.update(updateDTO.newData(), publicationOld);

        Publication publicationUpdated = publicationRepository.save(publicationOld);
        return publicationMapper.mapToDto(publicationUpdated);
    }

    @Override
    public String delete(String name) {
        Objects.requireNonNull(name, NAME_NULL);

        Publication publication = publicationRepository.getPublicationByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        publicationRepository.delete(publication);

        return String.format("Публикация %s удалена", name);
    }
}
