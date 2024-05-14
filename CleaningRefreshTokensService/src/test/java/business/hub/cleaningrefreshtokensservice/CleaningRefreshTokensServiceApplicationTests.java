package business.hub.cleaningrefreshtokensservice;

import business.hub.cleaningrefreshtokensservice.repository.RefreshTokenRepository;
import business.hub.cleaningrefreshtokensservice.service.CleaningRefreshTokensService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Тесты для класса CleaningRefreshTokensService.
 */
@ExtendWith(MockitoExtension.class)
class CleaningRefreshTokensServiceApplicationTests {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;


    private CleaningRefreshTokensService cleaningRefreshTokensService;

    @BeforeEach
    void setUp() {

        cleaningRefreshTokensService = new CleaningRefreshTokensService(refreshTokenRepository);
    }

    /**
     * Тест для метода cleanExpiredTokens.
     * Проверяет, вызывается ли метод deleteByExpirationBefore у refreshTokenRepository.
     */
    @Test
    void testCleanExpiredTokens() {

        cleaningRefreshTokensService.cleanExpiredTokens();
        verify(refreshTokenRepository, times(1)).deleteByExpirationBefore(any(LocalDateTime.class));

    }
}
