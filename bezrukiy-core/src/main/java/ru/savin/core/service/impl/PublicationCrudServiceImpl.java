package ru.savin.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.bezrukiy.shared.service.CrudService;
import ru.savin.core.dto.publication.PublicationDTO;
import ru.savin.core.entity.Publication;
import ru.savin.core.mapper.PublicationMapper;
import ru.savin.core.repository.PublicationRepository;

import java.util.Objects;

/**
 * CRUD для публикаций.
 */
@Service
@RequiredArgsConstructor
public class PublicationCrudServiceImpl implements CrudService<PublicationDTO, Publication> {
    private static final String ENTITY_NOT_FOUND = "Публикация с названием %s не найдена";

    private final PublicationMapper publicationMapper;
    private final PublicationRepository publicationRepository;

    @Override
    public PublicationDTO get(String name) {
        Objects.requireNonNull(name, "Название публикации null");

        Publication publication = publicationRepository.getByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        return publicationMapper.mapToDto(publication);
    }

    @Override
    public PublicationDTO save(Publication publication) {
        Objects.requireNonNull(publication, "Создание публикации невозможно. Тело запроса null");

        publication = publicationRepository.save(publication);
        return publicationMapper.mapToDto(publication);
    }

    @Override
    public PublicationDTO update(Publication publication) {
        Objects.requireNonNull(publication, "Обновление публикации невозможно. Тело запроса null");

        publication = publicationRepository.save(publication);
        return publicationMapper.mapToDto(publication);
    }

    @Override
    public String delete(String name) {
        Objects.requireNonNull(name, "Название публикации null");

        Publication publication = publicationRepository.getByName(name)
                .orElseThrow(() -> new BezrukiyRuntimeException(String.format(ENTITY_NOT_FOUND, name)));
        publicationRepository.delete(publication);

        return String.format("Публикация %s удалена", name);
    }
}
