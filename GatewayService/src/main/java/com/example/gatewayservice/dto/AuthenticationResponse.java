package com.example.gatewayservice.dto;


public class AuthenticationResponse {

    private String accessToken;

    private String refreshToken;

    /**
     * Конструктор класса AuthenticationResponse.
     *
     * @param accessTokenParam  токен доступа
     * @param refreshTokenParam токен обновления
     */
    public AuthenticationResponse(final String accessTokenParam,
                                  final String refreshTokenParam) {
        this.accessToken = accessTokenParam;
        this.refreshToken = refreshTokenParam;
    }

    /**
     * Пустой конструктор класса AuthenticationResponse.
     */
    public AuthenticationResponse() {
    }

    /**
     * Устанавливает токен доступа.
     *
     * @param accessTokenParam токен доступа
     */
    public void setAccessToken(final String accessTokenParam) {
        this.accessToken = accessTokenParam;
    }


    /**
     * Устанавливает токен обновления.
     *
     * @param refreshTokenParam токен обновления
     */
    public void setRefreshToken(final String refreshTokenParam) {
        this.refreshToken = refreshTokenParam;
    }

    /**
     * Возвращает токен доступа.
     *
     * @return токен доступа
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Возвращает токен обновления.
     *
     * @return токен обновления
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Переопределенный метод toString().
     *
     * @return accessToken + refreshToken
     */
    @Override
    public String toString() {
        return "AuthenticationResponse{"
               + "accessToken='" + accessToken + '\''
               + ", refreshToken='" + refreshToken + '\''
               + '}';
    }
}
