package ru.savin.files.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.savin.files.dto.FileStorageDTO;
import ru.savin.files.dto.FileStorageInfoDTO;
import ru.savin.files.dto.FileStorageUpdateDTO;
import ru.savin.files.service.FileStorageService;

import java.util.List;

/**
 * Контроллер файлов.
 */
@RestController
@RequestMapping("/file-storage")
@RequiredArgsConstructor
public class FileStorageController {
    private final FileStorageService fileStorageService;

    @GetMapping("/logo/{name}")
    public FileStorageInfoDTO getLogo(@PathVariable("name") String name) {
        return fileStorageService.getLogo(name);
    }

    @GetMapping("/{name}")
    public List<FileStorageInfoDTO> get(@PathVariable("name") String name) {
        return fileStorageService.getFiles(name);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void save(@RequestPart("newData") FileStorageDTO newData, @RequestPart("newFile") MultipartFile newFile) {
        fileStorageService.writeFile(newData, newFile);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void update(@RequestPart("updateData") FileStorageUpdateDTO updateData, @RequestPart("updateFile") MultipartFile updateFile) {
        fileStorageService.updateFile(updateData, updateFile);
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name) {
        return fileStorageService.deleteFile(name);
    }
}
