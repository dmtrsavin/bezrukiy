package ru.savin.files.converter;

import ru.savin.bezrukiy.shared.exception.BezrukiyRuntimeException;
import ru.savin.files.entity.enums.FileType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class FileTypeConverter implements AttributeConverter<FileType, String> {

    @Override
    public String convertToDatabaseColumn(FileType fileType) {
        if (fileType == null) {
            throw new BezrukiyRuntimeException("Конвертация невозможна, т.к fileType равен null");
        }

        return fileType.getType();
    }

    @Override
    public FileType convertToEntityAttribute(String s) {
        if (s == null) {
            throw new BezrukiyRuntimeException("Конвертация невозможна, т.к значение в БД равно null");
        }

        return Arrays.stream(FileType.values())
                .filter(x -> x.getType().equals(s))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
