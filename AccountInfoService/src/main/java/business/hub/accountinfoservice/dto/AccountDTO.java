package business.hub.accountinfoservice.dto;

import business.hub.accountinfoservice.model.Passport;
import lombok.Data;

/**
 * Объект передачи данных (DTO) для хранения информации об аккаунте.
 * Содержит электронную почту аккаунта и информацию о паспорте.
 */
@Data
public class AccountDTO {

    private String email;

    private Passport passport;
}
