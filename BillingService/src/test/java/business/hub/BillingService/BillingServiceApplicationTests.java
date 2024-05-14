package business.hub.BillingService;

import business.hub.models.Bill;
import business.hub.repositories.BillsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Класс, содержащий тесты для проверки функциональности {@link BillsRepository}.
 * Тесты выполняются с использованием встроенного контейнера Spring Boot.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BillingServiceApplicationTests {

    private static final BigDecimal TEST_BILL_AMOUNT_1 = BigDecimal.valueOf(1000.0);
    private static final BigDecimal TEST_BILL_AMOUNT_2 = BigDecimal.valueOf(2000.0);
    private static final BigDecimal TEST_BILL_AMOUNT_3 = BigDecimal.valueOf(3000.0);
    private static final BigDecimal TEST_BILL_AMOUNT_4 = BigDecimal.valueOf(4000.0);

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BillsRepository billsRepository;

    /**
     * Метод, вызываемый после выполнения каждого теста для очистки базы данных от данных.
     */
    @AfterEach
    public void resetDB() {
        billsRepository.deleteAll();
    }

    /**
     * Тест проверяет, что создание счета возвращает статус 201 (Created).
     */
    @Test
    void whenCreateBillThenStatus201() {

        Bill bill = new Bill(TEST_BILL_AMOUNT_1);
        ResponseEntity<Bill> response = restTemplate.postForEntity("/bills", bill, Bill.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getBillAmount(), is(TEST_BILL_AMOUNT_1));
        assertThat(response.getBody().getBillId(), notNullValue());
    }

    /**
     * Тест для проверки получения счета по идентификатору.
     *
     * @throws Exception если возникает ошибка при выполнении теста
     */
    @Test
    void givenBillWhenGetBillThenStatus200() {
        long id = createTestBill(TEST_BILL_AMOUNT_2).getBillId();
        Bill bill = restTemplate.getForObject("/bills/{id}", Bill.class, id);
        assertThat(bill.getBillAmount(), is(TEST_BILL_AMOUNT_2
                .setScale(2, RoundingMode.HALF_UP)));
    }

    /**
     * Тест для проверки обновления счета.
     *
     * @throws Exception если возникает ошибка при выполнении теста
     */
    @Test
    void whenUpdateBillThenStatus200() {
        long id = createTestBill(TEST_BILL_AMOUNT_3).getBillId();
        Bill bill = new Bill(TEST_BILL_AMOUNT_3);
        HttpEntity<Bill> entity = new HttpEntity<>(bill);
        ResponseEntity<Bill> response = restTemplate.exchange("/bills/{id}",
                HttpMethod.PUT, entity, Bill.class, id);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getBillId(), notNullValue());
        assertThat(response.getBody().getBillAmount(), is(TEST_BILL_AMOUNT_3));
    }

    /**
     * Тест для проверки удаления счета.
     *
     * @throws Exception если возникает ошибка при выполнении теста
     */
    @Test
    void givenBillWhenDeleteBillThenStats200() {
        long id = createTestBill(TEST_BILL_AMOUNT_4).getBillId();
        ResponseEntity<Bill> response = restTemplate.exchange("/bills/{id}",
                HttpMethod.DELETE, null, Bill.class, id);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getBillId(), is(id));
        assertThat(response.getBody().getBillAmount(), is(TEST_BILL_AMOUNT_4
                .setScale(2, RoundingMode.HALF_UP)));
    }

    /**
     * Метод для создания тестового счета.
     *
     * @param amount сумма счета
     * @return созданный счет
     */
    private Bill createTestBill(final BigDecimal amount) {
        Bill bill = new Bill(amount);
        bill.setBillCreatedAt(LocalDateTime.now());
        return billsRepository.save(bill);
    }

}

