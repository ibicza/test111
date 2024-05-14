package business.hub.controllers;

import business.hub.dto.NewBillDTO;
import business.hub.dto.UpdateBillDTO;
import business.hub.models.Bill;
import business.hub.services.BillServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller счетов на оплату.
 * Перейдя по /bills/id мы получим счет человек в виде Bill.
 *
 * @author Anatoly Zakharov
 */
@Slf4j
@Tag(name = "Main methods")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bills")
public class BillingRESTController {

    private final BillServiceImpl billService;
    private final ModelMapper modelMapper;


    /**
     * Получение счета на оплату по идентификатору.
     *
     * @param id идентификатор счета на оплату
     * @return ResponseEntity с объектом счета на оплату
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение счета на оплату по id"
    )
    public ResponseEntity<Bill> getBill(final @PathVariable long id) {
        log.info("GETTING BILL WITH ID {}", id);
        Bill bill = billService.getBill(id);
        log.info("GETTING BILL WITH ID {} HAS BEEN COMPLETED", id);
        return ResponseEntity.ok().body(bill);
    }

    /**
     * Создание нового счета на оплату.
     *
     * @param newBillDTO объект, содержащий данные для создания нового счета
     * @return ResponseEntity с объектом созданного счета на оплату
     */
    @PostMapping
    @Operation(
            summary = "Создание нового счета на оплату"
    )
    public ResponseEntity<Bill> addBill(final @RequestBody NewBillDTO newBillDTO) {
        log.info("ADDING NEW BILL");
        Bill bill = convertToBill(newBillDTO);
        billService.saveBill(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body(bill);
    }

    /**
     * Изменение счета на оплату по идентификатору.
     *
     * @param id            идентификатор счета на оплату
     * @param updateBillDTO объект, содержащий данные для изменения счета
     * @return ResponseEntity с объектом измененного счета на оплату
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение счета на оплату по id"
    )
    public ResponseEntity<Bill> editBill(final @PathVariable long id,
                                         final @RequestBody UpdateBillDTO updateBillDTO) {
        log.info("EDIT BILL WITH ID {}", id);
        billService.updateBill(id, convertToUpdateBill(updateBillDTO));
        log.info("EDIT BILL WITH ID {} HAS BEEN COMPLETED", id);
        return ResponseEntity.ok(billService.getBill(id));
    }

    /**
     * Удаление счета на оплату по идентификатору.
     *
     * @param id идентификатор счета на оплату
     * @return ResponseEntity с объектом удаленного счета на оплату
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление счета на оплату по id"
    )
    public ResponseEntity<Bill> deleteBill(final @PathVariable long id) {
        log.info("DELETE BILL WITH ID {}", id);
        Bill bill = billService.getBill(id);
        billService.deleteBill(id);
        log.info("DELETE BILL WITH ID {} HAS BEEN COMPLETED", id);
        return ResponseEntity.ok().body(bill);
    }

    /**
     * Преобразует объект NewBillDTO в объект Bill.
     *
     * @param newBillDTO объект с данными для создания счета
     * @return объект Bill, созданный на основе переданного DTO
     */
    public Bill convertToBill(final NewBillDTO newBillDTO) {
        return modelMapper.map(newBillDTO, Bill.class);
    }

    /**
     * Преобразует объект UpdateBillDTO в объект Bill.
     *
     * @param updateBillDTO объект с данными для изменения счета
     * @return объект Bill, измененный на основе переданного DTO
     */
    public Bill convertToUpdateBill(final UpdateBillDTO updateBillDTO) {
        return modelMapper.map(updateBillDTO, Bill.class);
    }

}
