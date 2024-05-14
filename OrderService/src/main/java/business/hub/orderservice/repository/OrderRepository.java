package business.hub.orderservice.repository;

import business.hub.orderservice.entity.Order;
import business.hub.orderservice.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Репозиторий для работы с заказами.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Находит все заказы по идентификатору пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список заказов пользователя
     */
    List<Order> findAllByUserId(Long userId);

    /**
     * Поиск заказов с использованием различных параметров.
     *
     * @param userId     идентификатор пользователя (может быть null)
     * @param status     статус заказа (может быть null)
     * @param addressId  идентификатор адреса доставки (может быть null)
     * @param dateFrom   дата начала периода (может быть null)
     * @param dateTo     дата окончания периода (может быть null)
     * @param pageable   объект постраничности и сортировки
     * @return страница заказов, удовлетворяющих переданным параметрам
     */
    @Query("SELECT t FROM Order t where "
            + "(:userId is null or t.userId=:userId) and " // учитываем, что параметр может быть null или пустым
            + "(:status is null or t.orderStatus=:status) and "
            + "(:addressId is null or t.addressId=:addressId) and"
            + "("
            + "(cast(:dateFrom as timestamp) is null or t.createdAt>=:dateFrom) and "
            + "(cast(:dateTo as timestamp) is null or t.createdAt<=:dateTo)"
            + ")"
    )
    Page<Order> findByParams(@Param("userId") Long userId,
                             @Param("status") OrderStatus status,
                             @Param("addressId") Long addressId,
                             @Param("dateFrom") Date dateFrom,
                             @Param("dateTo") Date dateTo,
                             Pageable pageable);
}
