package ru.savin.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Запуск сервера Эврики для "Безрукий".
 */
@SpringBootApplication
@EnableEurekaServer
public class BezrukiyEurekaServerStarted {
    public static void main(String[] args) {
        SpringApplication.run(BezrukiyEurekaServerStarted.class, args);
    }
}