package business.hub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * dto BlockAccountInfoDto для обмена данными.
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockAccountInfoDto {

    private Long accountId;
    private String reason;
}
