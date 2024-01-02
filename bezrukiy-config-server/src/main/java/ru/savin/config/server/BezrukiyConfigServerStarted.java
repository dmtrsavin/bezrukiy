package ru.savin.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Запуск конфигурационного сервиса для "Безрукий".
 */
@SpringBootApplication
@EnableConfigServer
public class BezrukiyConfigServerStarted {
    public static void main(String[] args) {
        SpringApplication.run(BezrukiyConfigServerStarted.class, args);
    }
}