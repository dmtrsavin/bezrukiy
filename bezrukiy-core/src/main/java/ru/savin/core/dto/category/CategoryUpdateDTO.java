package ru.savin.core.dto.category;

/**
 * ДТО для обновления категорий.
 *
 * @param oldName нынешнее название.
 * @param updateData новые данные.
 */
public record CategoryUpdateDTO (String oldName, CategoryDTO updateData) {
}
