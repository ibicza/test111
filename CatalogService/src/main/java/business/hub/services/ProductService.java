package business.hub.services;

import business.hub.models.Product;

import java.util.List;

public interface ProductService {
    /**
     * Получает список всех продуктов.
     *
     * @return список всех продуктов
     */
    List<Product> getAllProducts();
    /**
     * Получает информацию о продукте по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return информация о продукте
     */
    Product getProduct(int id);
    /**
     * Сохраняет новый продукт.
     *
     * @param product новый продукт для сохранения
     */

    void saveProduct(Product product);
    /**
     * Обновляет информацию о продукте по его идентификатору.
     *
     * @param id      идентификатор продукта
     * @param product новая информация о продукте
     */
    void updateProduct(int id, Product product);
    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id идентификатор продукта
     */
    void deleteProduct(int id);
}
