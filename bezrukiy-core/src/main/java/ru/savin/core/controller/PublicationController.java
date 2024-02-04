package ru.savin.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savin.bezrukiy.shared.service.CrudService;
import ru.savin.core.dto.publication.PublicationDTO;
import ru.savin.core.dto.publication.PublicationUpdateDTO;

/**
 * Контроллер публикаций.
 */
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicationController {
    private final CrudService<PublicationDTO, PublicationUpdateDTO> crudService;

    @GetMapping("/{name}")
    public PublicationDTO get(@PathVariable("name") String name) {
        return crudService.get(name);
    }

    @PostMapping
    public PublicationDTO save(@RequestBody PublicationDTO publicationDTO) {
        return crudService.save(publicationDTO);
    }

    @PutMapping
    public PublicationDTO update(@RequestBody PublicationUpdateDTO updateDTO) {
        return crudService.update(updateDTO);
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name) {
        return crudService.delete(name);
    }

}
