package com.example.gatewayservice.Util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для проверки доступа пользователя к определенным путям в приложении на основе его ролей.
 */
@Component
public class PathFilterUtil {

    private static final Map<String, List<String>> PATH_TO_ROLES_MAP = new HashMap<>();

    static {
        PATH_TO_ROLES_MAP.put("/api/users/**", List.of("ROLE_USER", "ROLE_ADMIN"));
        PATH_TO_ROLES_MAP.put("/api/admin/**", List.of("ROLE_ADMIN"));
    }

    /**
     * Метод для проверки, разрешен ли пользователю с данными ролями доступ к данному пути.
     * @param path запрашиваемый путь
     * @param userRoles роли пользователя
     * @return true, если доступ разрешен, false в противном случае
     */
    public boolean isAccessAllowed(final String path,
                                   final List<String> userRoles) {
        for (Map.Entry<String, List<String>> entry : PATH_TO_ROLES_MAP.entrySet()) {
            String key = entry.getKey().replace("**", ".*");
            List<String> rolesAllowed = entry.getValue();

            if (path.matches(key)) {
                for (String userRole : userRoles) {
                    if (rolesAllowed.contains(userRole)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
