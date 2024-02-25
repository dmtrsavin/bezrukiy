package ru.savin.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Запуск приложения "Безрукий".
 */
@RefreshScope
@SpringBootApplication
public class BezrukiyCoreStarted {
    public static void main(String[] args) {
        SpringApplication.run(BezrukiyCoreStarted.class, args);
    }
}