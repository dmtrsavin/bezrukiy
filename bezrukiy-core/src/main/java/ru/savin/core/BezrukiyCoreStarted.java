package ru.savin.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Запуск приложения "Безрукий".
 */
@SpringBootApplication
public class BezrukiyCoreStarted {
    public static void main(String[] args) {
        SpringApplication.run(BezrukiyCoreStarted.class, args);
    }
}