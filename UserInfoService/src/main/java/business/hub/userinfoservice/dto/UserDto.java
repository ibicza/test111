package business.hub.userinfoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
}
