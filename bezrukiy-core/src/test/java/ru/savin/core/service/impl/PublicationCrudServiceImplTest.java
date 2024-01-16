package ru.savin.core.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.core.dto.publication.PublicationDTO;
import ru.savin.core.dto.publication.PublicationUpdateDTO;
import ru.savin.core.entity.Publication;
import ru.savin.core.mapper.PublicationMapper;
import ru.savin.core.repository.PublicationRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

@SpringJUnitConfig
class PublicationCrudServiceImplTest {
    @Mock
    private PublicationRepository publicationRepository;
    @Mock
    private PublicationMapper publicationMapper;

    @InjectMocks
    private PublicationCrudServiceImpl crudService;

    @Test
    void getPublication_requestIsNull_nullPointerException() {
        //then
        Assertions.assertThrows(NullPointerException.class, () -> crudService.get(null));
    }

    @Test
    void getPublication_notFoundCategory_bezrukiyRuntimeException() {
        //given
        String name = "name";

        //when
        Mockito.when(publicationRepository.getPublicationByName(name))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.get(name));
    }

    @Test
    void getPublication_requestIsNotNull_success() {
        //given
        String name = "name";
        Publication entity = generatePublication();
        PublicationDTO expected = generatePublicationDTO();

        //when
        Mockito.when(publicationRepository.getPublicationByName(name))
                .thenReturn(Optional.of(entity));
        Mockito.when(publicationMapper.mapToDto(entity))
                .thenReturn(expected);

        //then
        Assertions.assertEquals(expected, crudService.get(name));
    }

    @Test
    void savePublication_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.save(null));
    }

    @Test
    void savePublication_requestIsNotNull_success() {
        //given
        Publication entity = generatePublication();
        PublicationDTO expected = generatePublicationDTO();

        //when
        Mockito.when(publicationMapper.mapToEntity(expected)).thenReturn(entity);
        Mockito.when(publicationRepository.save(entity)).thenReturn(entity);
        Mockito.when(publicationMapper.mapToDto(entity)).thenReturn(expected);

        //then
        Assertions.assertEquals(expected, crudService.save(expected));
    }

    @Test
    void updatePublication_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.update(null));
    }

    @Test
    void updatePublication_nameIsNull_nullPointerException() {
        //given
        PublicationDTO data = generatePublicationDTO();
        PublicationUpdateDTO updateDTO = new PublicationUpdateDTO(null, data);

        //then
        Assertions.assertThrows(NullPointerException.class, () -> crudService.update(updateDTO));
    }

    @Test
    void updatePublication_notFoundCategory_bezrukiyRuntimeException() {
        //given
        PublicationDTO data = generatePublicationDTO();
        PublicationUpdateDTO updateDTO = new PublicationUpdateDTO("name", data);

        //when
        Mockito.when(publicationRepository.getPublicationByName(updateDTO.oldName()))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.update(updateDTO));
    }

    @Test
    void updatePublication_requestIsNotNull_success() {
        //given
        PublicationDTO data = generatePublicationDTO();
        PublicationUpdateDTO updateDTO = new PublicationUpdateDTO("name", data);
        Publication entity = generatePublication();

        //when
        Mockito.when(publicationRepository.getPublicationByName(updateDTO.oldName()))
                .thenReturn(Optional.of(entity));
        Mockito.when(publicationRepository.save(entity))
                .thenReturn(entity);
        Mockito.when(publicationMapper.mapToDto(entity))
                .thenReturn(data);

        //then
        crudService.update(updateDTO);
        Mockito.verify(publicationMapper, Mockito.times(1)).update(data, entity);
        Assertions.assertEquals(data, crudService.update(updateDTO));
    }

    @Test
    void deleteCategory_requestIsNull_nullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> crudService.delete(null));
    }

    @Test
    void deleteCategory_notFoundCategory_bezrukiyRuntimeException() {
        //given
        String name = "name";

        //when
        Mockito.when(publicationRepository.getPublicationByName(name))
                .thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(BezrukiyRuntimeException.class, () -> crudService.delete(name));
    }

    @Test
    void deleteCategory_requestIsNotNull_success() {
        //given
        String name = "name";
        Publication entity = generatePublication();

        //when
        Mockito.when(publicationRepository.getPublicationByName(name))
                .thenReturn(Optional.of(entity));

        //then
        String result = crudService.delete(name);
        Mockito.verify(publicationRepository, Mockito.times(1)).delete(entity);
        Assertions.assertEquals(String.format("Публикация %s удалена", name), result);
    }

    private Publication generatePublication() {
        Publication publication = new Publication();
        publication.setId(1L);
        publication.setName("name");
        publication.setCreateDttm(OffsetDateTime.now());

        return publication;
    }

    private PublicationDTO generatePublicationDTO() {
        return new PublicationDTO(
                "name", OffsetDateTime.now(),OffsetDateTime.now(), null, null);
    }
}