package com.example.gatewayservice.Util;

import com.example.gatewayservice.configuration.ServiceUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Класс, позволяющий получать конфигурацию сервисов в реальном времени.
 */
@Component
@RefreshScope
public class RealTimeServiceLocator {

    @Autowired
    private ServiceUrlsConfig serviceUrlsConfig;

    /**
     * Метод, получающий URL-адрес сервиса в реальном времени.
     * @param serviceName Имя сервиса, для которого запрашивается URL адрес
     * @return Строка с URL адресом
     */
    public String getUrl(final String serviceName) {
        return serviceUrlsConfig.getUrls().get(serviceName);
    }
}
