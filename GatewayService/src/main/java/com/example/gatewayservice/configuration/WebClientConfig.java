package com.example.gatewayservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Конфигурационный класс для настройки WebClient.
 */
@Configuration
public class WebClientConfig {


    /**
     * Создает и настраивает WebClient с заголовком Content-Type = application/json.
     * Настраивает кодировщик и декодировщик для сериализации/десериализации объектов JSON.
     *
     * @return настроенный WebClient
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .codecs(configurer -> {
                    configurer.defaultCodecs().jackson2JsonEncoder(
                            new Jackson2JsonEncoder(new ObjectMapper(),
                                    MediaType.APPLICATION_JSON));
                    configurer.defaultCodecs().jackson2JsonDecoder(
                            new Jackson2JsonDecoder(new ObjectMapper(),
                                    MediaType.APPLICATION_JSON));
                })
                .build();
    }
}
