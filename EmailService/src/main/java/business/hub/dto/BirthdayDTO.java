package business.hub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для представления информации о дне рождения.
 */
@Data
@AllArgsConstructor
public class BirthdayDTO {
    private String email;
    private String firstName;
    private String lastName;
}
