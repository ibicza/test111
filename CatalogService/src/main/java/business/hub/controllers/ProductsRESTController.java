package business.hub.controllers;

import business.hub.dto.ProductDTO;
import business.hub.models.Product;
import business.hub.services.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST контроллер для управления продуктами.
 * Этот контроллер обрабатывает HTTP-запросы, связанные с операциями CRUD для продуктов.
 * При переходе по различным запросам выполняются следующие действия:
 * - GET /api/products: Получение списка всех продуктов в виде DTO.
 * - GET /api/products/{id}: Получение информации о конкретном продукте
 * по его идентификатору в виде DTO.
 * - POST /api/products: Добавление нового продукта на основе предоставленных данных в теле запроса.
 * - PUT /api/products/{id}: Редактирование информации о продукте по
 * его идентификатору на основе предоставленных данных в теле запроса.
 * - DELETE /api/products/{id}: Удаление продукта по его идентификатору.
 */
@Tag(name = "Main methods")
@RestController
@RequestMapping("/api/products")
public class ProductsRESTController {
    private final ProductServiceImpl productService;
    private final ModelMapper modelMapper;

    /**
     * Конструктор для ProductsRESTController.
     * @param productServiceParam - {@link ProductServiceImpl}
     * @param modelMapperParam - {@link ModelMapper}
     */
    @Autowired
    public ProductsRESTController(final ProductServiceImpl productServiceParam,
                                  final ModelMapper modelMapperParam) {
        this.productService = productServiceParam;
        this.modelMapper = modelMapperParam;
    }

    /**
     * Получение списка всех продуктов.
     *
     * @return список DTO продуктов
     */
    @GetMapping
    @Operation(
            summary = "Получение списка продуктов",
            description = "Получает DTO продуктов"
    )
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> list = productService.getAllProducts().stream().map(this::convertToProductDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
    /**
     * Получение информации о конкретном продукте по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return информация о продукте в виде DTO
     */
    @GetMapping({"/{id}"})
    public ResponseEntity<ProductDTO> getProduct(final @PathVariable int id) {
        ProductDTO productDTO = convertToProductDTO(productService.getProduct(id));
        return ResponseEntity.ok(productDTO);
    }
    /**
     * Добавление нового продукта.
     *
     * @param productDTO DTO нового продукта
     * @return статус ответа: OK, если продукт успешно добавлен
     */
    @PostMapping
    public ResponseEntity<HttpStatus> addProduct(final @RequestBody ProductDTO productDTO) {
        productService.saveProduct(convertToProduct(productDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    /**
     * Редактирование информации о продукте.
     *
     * @param id         идентификатор продукта
     * @param productDTO новая информация о продукте в виде DTO
     * @return статус ответа: OK, если информация о продукте успешно обновлена
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> editProduct(final @PathVariable int id,
                                                  final @RequestBody ProductDTO productDTO) {
        productService.updateProduct(id, convertToProduct(productDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    /**
     * Удаление продукта.
     *
     * @param id идентификатор продукта
     * @return статус ответа: OK, если продукт успешно удален
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(final @PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Product convertToProduct(final ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    private ProductDTO convertToProductDTO(final Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

}
