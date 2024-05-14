package business.hub.cleaningrefreshtokensservice.service;

import business.hub.cleaningrefreshtokensservice.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Сервис для очистки просроченных обновляемых токенов.
 */
@Service
@RequiredArgsConstructor
@EnableScheduling
public class CleaningRefreshTokensService {
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Метод для очистки просроченных токенов.
     * Удаляет токены из репозитория, просроченные к текущему времени.
     */
    public void cleanExpiredTokens() {
        LocalDateTime currentTime = LocalDateTime.now();
        refreshTokenRepository.deleteByExpirationBefore(currentTime);
    }
}
