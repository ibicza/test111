package business.hub.services;
import business.hub.models.Product;
import business.hub.repositories.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервисный класс продуктов.
 * @author Anatoly Zakharov
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductsRepository productsRepository;

    /**
     * Конструктор для ProductServiceImpl.
     * @param productsRepositoryParam - {@link ProductsRepository}
     */
    @Autowired
    public ProductServiceImpl(final ProductsRepository productsRepositoryParam) {
        this.productsRepository = productsRepositoryParam;
    }

    /**
     * Функция получения списка всех продуктов.
     * @return возвращает список всех продуктов
     */
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productsRepository.findAll();
        log.info("Get all products: {}", products);
        return products;

    }

    /**
     * Функция получения продукта по идентификатору.
     * @param id - идентификатор
     * @return возвращает продукт
     */
    @Override
    public Product getProduct(final int id) {
        Optional<Product> getProduct = productsRepository.findById(id);
        log.info("Get product: {}", getProduct.orElse(null));
        return getProduct.orElse(null);
    }

    /**
     * Процедура создания нового продукта.
     * @param product - новый объект типа Product
     */
    @Transactional
    @Override
    public void saveProduct(final Product product) {
        enrichProduct(product);
        Product saved = productsRepository.save(product);
        log.info("Success save product: {}", saved);

    }

    /**
     * Процедура изменения существующего продукта.
     * @param id - идентификатор изменяемого продукта
     * @param updateProduct - объект типа Product
     */
    @Transactional
    @Override
    public void updateProduct(final int id,
                              final Product updateProduct) {
        updateProduct.setProductId(id);
        updateProduct.setCreatedAt(this.getProduct(id).getCreatedAt());
        updateProduct.setUpdatedAt(LocalDateTime.now());
        Product updated = productsRepository.save(updateProduct);
        log.info("Success update product: {}", updated);
    }

    /**
     * Процедура удаления существующего продукта.
     * @param id - идентификатор удаляемого продукта
     */
    @Transactional
    @Override
    public void deleteProduct(final int id) {
        productsRepository.deleteById(id);
        log.info("Success delete by id: {}", id);
    }

    /**
     * Процедура определения времени создания нового продукта
     * и изменения существующего продукта.
     * @param  product - объект типа Product
     */
    private void enrichProduct(final Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
    }
}
