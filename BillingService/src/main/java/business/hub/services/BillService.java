package business.hub.services;

import business.hub.models.Bill;


public interface BillService {
    /**
     * Получает счет по id.
     *
     * @param id Идентификатор счета
     * @return Объект счета, если найден, иначе null
     */
    Bill getBill(long id);
    /**
     * Сохраняет счет.
     *
     * @param bill Объект счета для сохранения
     */
    void saveBill(Bill bill);
    /**
     * Обновляет информацию о счете.
     *
     * @param id Идентификатор счета для обновления
     * @param bill Объект счета с обновленными данными
     */
    void updateBill(long id, Bill bill);

    /**
     * Удаляет счет по его идентификатору.
     *
     * @param id Идентификатор счета для удаления
     */
    void deleteBill(long id);
}
