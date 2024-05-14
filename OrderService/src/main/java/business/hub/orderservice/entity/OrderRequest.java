package business.hub.orderservice.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @NotNull
    private Long userId;

    @NotNull
    @Valid
    private List<OrderItem> orderItems;

    @NotNull
    private Long addressId;

}
