package business.hub.cleaningrefreshtokensservice.scheduler;

import business.hub.cleaningrefreshtokensservice.service.CleaningRefreshTokensService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Планировщик для очистки просроченных токенов.
 */
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final CleaningRefreshTokensService cleaningService;

    /**
     * Метод, вызываемый по расписанию для очистки просроченных токенов.
     * Использует сервис очистки просроченных токенов.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void cleanExpiredTokens() {
        cleaningService.cleanExpiredTokens();
    }
}
