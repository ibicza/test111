package com.example.gatewayservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс для представления запроса на обновление токена.
 */
@Data
@NoArgsConstructor
public class RefreshRequest {

    /**
     * Токен обновления.
     */
    @JsonProperty("refreshToken")
    private String refreshToken;

    /**
     * Конструктор класса RefreshRequest.
     *
     * @param refreshTokenParam токен обновления
     */
    public RefreshRequest(final String refreshTokenParam) {
        this.refreshToken = refreshTokenParam;
    }
}
