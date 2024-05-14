package business.hub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author Igor Ostrovsky
 * Class DataTransferObject will receive data from the Kafka dto-topic
 * to create ProfileEvent Entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoCreateEvent {

    @JsonProperty("account_id")
    String accountId;
    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("last_name")
    String lastName;

}
