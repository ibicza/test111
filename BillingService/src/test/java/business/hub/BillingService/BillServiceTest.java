package business.hub.BillingService;

import business.hub.models.Bill;
import business.hub.repositories.BillsRepository;
import business.hub.services.BillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BillServiceImpl.class)
@ExtendWith(MockitoExtension.class)
public class BillServiceTest {


    @MockBean
    private BillsRepository billsRepository;

    @Autowired
    private BillServiceImpl billService;

    private    Bill bill;

    @BeforeEach
    void setUp () {

        bill = new Bill();
        bill.setBillAmount(new BigDecimal(11));
        bill.setPaid(true);
        bill.setBillCreatedAt(LocalDateTime.now());
        bill.setBillId(1L);
        bill.setBillUpdatedAt(LocalDateTime.now());
        bill.setBillPaidAt(LocalDateTime.now());
    }

    @Test
    void getBillTest() {

        when(billsRepository.findById(anyLong())).thenReturn(Optional.of(bill));

        Bill res = billService.getBill(1L);

        assertNotNull(res);
    }

    @Test
    void saveBillTest() {

        billService.saveBill(bill);

        verify(billsRepository, times(1)).save(bill);
    }

    @Test
    void updateBillTest () {
        Bill billUpdate = new Bill();

        billUpdate.setBillAmount(new BigDecimal(13));
        billUpdate.setPaid(true);
        billUpdate.setBillCreatedAt(LocalDateTime.now());
        billUpdate.setBillId(1L);
        billUpdate.setBillUpdatedAt(LocalDateTime.now());
        billUpdate.setBillPaidAt(LocalDateTime.now());

        when(billsRepository.findById(bill.getBillId())).thenReturn(Optional.of(bill));

        billService.updateBill(bill.getBillId(), billUpdate);

        verify(billsRepository, times(2)).findById(bill.getBillId());
        verify(billsRepository, times(1)).save(any(Bill.class));
    }

    @Test
    void deleteBillTest () {
        when(billsRepository.findById(bill.getBillId())).thenReturn(Optional.of(bill));
        billService.deleteBill(bill.getBillId());

        verify(billsRepository, times(1)).findById(bill.getBillId());
        verify(billsRepository, times(1)).deleteById(bill.getBillId());
    }


}
