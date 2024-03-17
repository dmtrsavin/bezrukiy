package ru.savin.files.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация расположения файлов.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "files")
public class FilesConfig {
    private String logo;
    private String image;
    private String info;
}
