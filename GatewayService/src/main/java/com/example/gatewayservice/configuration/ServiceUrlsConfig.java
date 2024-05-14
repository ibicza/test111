package com.example.gatewayservice.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс конфигурации для подгрузки URL-адресов сервисов из проперти файла.
 */
@Configuration
@ConfigurationProperties(prefix = "service")
@Getter
public class ServiceUrlsConfig {

    /**
     * Мапа, содержащая имена сервисов и их URL-адреса.
     */
    private final Map<String, String> urls = new HashMap<>();

    /**
     * Получаем URL-адреса.
     * @return urls - адреса
     */
    public Map<String, String> getUrls() {
        return urls;
    }
}
