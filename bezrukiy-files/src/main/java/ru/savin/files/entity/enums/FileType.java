package ru.savin.files.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Типы файлов и расширение.
 */
@Getter
@AllArgsConstructor
public enum FileType {
    LOGO("logo", ".png"),
    IMAGE("image", ".jpg"),
    TEXT("text", ".txt");

    private final String type;
    private final String extension;
}
