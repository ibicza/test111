package business.hub.orderservice.entity.search;

import business.hub.orderservice.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchValues {

    private Long userId;
    private OrderStatus orderStatus;
    private Long addressId;

    // для задания периода по датам
    private Date dateFrom;
    private Date dateTo;

    // постраничность
    private Integer pageNumber;
    private Integer pageSize;

    // сортировка
    private String sortColumn;
    private String sortDirection;

    // такие же названия должны быть у объекта на frontend
}
