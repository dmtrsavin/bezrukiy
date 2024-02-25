package ru.savin.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Запуск приложения файловой составляющей.
 */
@RefreshScope
@SpringBootApplication
public class BezrukiyFilesStarted {
    public static void main(String[] args) {
        SpringApplication.run(BezrukiyFilesStarted.class, args);
    }
}