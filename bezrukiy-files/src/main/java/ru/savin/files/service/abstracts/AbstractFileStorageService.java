package ru.savin.files.service.abstracts;

import org.apache.commons.io.FilenameUtils;
import ru.savin.files.config.FilesConfig;
import ru.savin.files.entity.enums.FileType;

/**
 * Общие свойства для создания файлов.
 */
public abstract class AbstractFileStorageService extends AbstractFileService{

    public AbstractFileStorageService(FilesConfig filesConfig) {
        super(filesConfig);
    }

    protected String getExtension(String name) {
        return FilenameUtils.getExtension(name);
    }

    protected boolean isLogo(String extension) {
        return extension.equals(FileType.LOGO.getExtension());
    }

    protected boolean isImage(String extension) {
        return extension.equals(FileType.IMAGE.getExtension());
    }

    protected boolean isText(String extension) {
        return extension.equals(FileType.TEXT.getExtension());
    }

    protected String getTextName(String nameFileText) {
        return nameFileText + FileType.TEXT.getExtension();
    }

    protected String getLogoName(String nameFileLogo) {
        return nameFileLogo + FileType.LOGO.getExtension();
    }

    protected String getImageName(String nameFileImage) {
        return nameFileImage + FileType.IMAGE.getExtension();
    }
}
