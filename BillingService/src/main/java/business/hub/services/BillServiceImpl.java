package business.hub.services;

import business.hub.models.Bill;
import business.hub.repositories.BillsRepository;
import business.hub.util.MyEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Сервисный класс счетов на оплату.
 *
 * @author Anatoly Zakharov
 */
@Service
@Transactional(readOnly = true)
public class BillServiceImpl implements BillService {

    private final BillsRepository billsRepository;
    /**
     * Конструктор для создания BillsRepository.
     * @param billsRepositoryParam - {@link BillsRepository}
     */
    @Autowired
    public BillServiceImpl(final BillsRepository billsRepositoryParam) {
        this.billsRepository = billsRepositoryParam;
    }

    /**
     * Функция получения счета на оплату по идентификатору.
     *
     * @param id - идентификатор
     * @return возвращает счет на оплату
     */
    @Override
    public Bill getBill(final long id) {
        Optional<Bill> getBill = billsRepository.findById(id);
        if (getBill.isEmpty()) {
            throw new MyEntityNotFoundException(id);
        }
        return getBill.get();
    }

    /**
     * Процедура создания нового счета на оплату.
     *
     * @param bill - новый объект типа Bill
     */

    @Override
    @Transactional
    public void saveBill(final Bill bill) {
        enrichBill(bill);
        billsRepository.save(bill);
    }

    /**
     * Процедура определения времени создания нового счета
     * и отметка, что счет не оплачен.
     *
     * @param bill - объект типа Bill
     */
    private void enrichBill(final Bill bill) {
        bill.setBillCreatedAt(LocalDateTime.now());
        bill.setPaid(false);
    }

    /**
     * Процедура изменения существующего счета на оплату.
     *
     * @param id         - идентификатор изменяемого счета на оплату
     * @param updateBill - объект типа Bill
     */
    @Override
    @Transactional
    public void updateBill(final long id, final Bill updateBill)
            throws MyEntityNotFoundException {
        updateBill.setBillId(id);
        updateBill.setBillCreatedAt(this.getBill(id).getBillCreatedAt());
        if (updateBill.isPaid()) {
            updateBill.setBillPaidAt(LocalDateTime.now());
            updateBill.setBillUpdatedAt(this.getBill(id).getBillUpdatedAt());
        } else {
            updateBill.setBillUpdatedAt(LocalDateTime.now());
        }
        billsRepository.save(updateBill);

    }

    /**
     * Процедура удаления существующего счета на оплату.
     *
     * @param id - идентификатор удаляемого счета на оплату
     */

    @Override
    @Transactional
    public void deleteBill(final long id) throws MyEntityNotFoundException {
        Optional<Bill> bill = billsRepository.findById(id);
        if (bill.isPresent()) {
            billsRepository.deleteById(id);
        } else {
            throw new MyEntityNotFoundException(id);
        }
    }


}
