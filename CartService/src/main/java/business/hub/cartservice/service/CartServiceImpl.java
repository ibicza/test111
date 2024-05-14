package business.hub.cartservice.service;

import business.hub.cartservice.model.CartProduct;
import business.hub.cartservice.model.Product;
import business.hub.cartservice.model.User;
import business.hub.cartservice.repositories.CartProductRepositories;
import business.hub.cartservice.repositories.CartRepositories;
import business.hub.cartservice.repositories.ProductRepositories;
import business.hub.cartservice.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с корзиной пользователя.
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepositories cartRepositories;
    private final CartProductRepositories cartProductRepositories;
    private final ProductRepositories productRepositories;
    private final UserRepositories userRepositories;

    /**
     * Конструктор для CartServiceImpl.
     * @param cartRepositoriesParam - {@link CartRepositories}
     * @param cartProductRepositoriesParam - {@link CartProductRepositories}
     * @param productRepositoriesParam - {@link ProductRepositories}
     * @param userRepositoriesParam - {@link UserRepositories}
     */
    @Autowired
    public CartServiceImpl(final CartRepositories cartRepositoriesParam,
                           final CartProductRepositories cartProductRepositoriesParam,
                           final ProductRepositories productRepositoriesParam,
                           final UserRepositories userRepositoriesParam) {
        this.cartRepositories = cartRepositoriesParam;
        this.cartProductRepositories = cartProductRepositoriesParam;
        this.productRepositories = productRepositoriesParam;
        this.userRepositories = userRepositoriesParam;
    }


    /**
     * Получает список продуктов в корзине пользователя по его идентификатору.
     *
     * @param userid идентификатор пользователя
     * @return список продуктов в корзине пользователя
     */
    @Override
    public List<CartProduct> getProductById(final int userid) {
        User user = userRepositories.findById(userid).orElse(null);
        if (user != null) {
            return cartProductRepositories.findAll().stream().filter(cartProduct ->
                    cartProduct.getCart().equals(user.getCart())
            ).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Добавляет указанный продукт в корзину пользователя.
     *
     * @param cartProduct продукт для добавления в корзину
     * @return добавленный продукт в корзине пользователя
     */
    @Override
    public CartProduct postUserCartProduct(final CartProduct cartProduct) {
        productRepositories.save(cartProduct.getProduct());
        cartProductRepositories.save(cartProduct);
        return cartProduct;
    }

    /**
     * Изменяет количество продукта в корзине пользователя.
     *
     * @param userId   идентификатор пользователя
     * @param productId идентификатор продукта
     * @param quantity  новое количество продукта
     * @return продукт в корзине пользователя с обновленным количеством
     */
    @Override
    public CartProduct putCartCount(final int userId,
                                    final int productId,
                                    final int quantity) {
        User user = userRepositories.findById(userId).orElse(null);
        Product product = productRepositories.findById(productId).orElse(null);
        if (user != null) {
            CartProduct cartProduct1 = cartProductRepositories.findAll().stream().filter(cartProduct ->
                    cartProduct.getCart().equals(user.getCart())
            ).filter(cartProduct ->
                    cartProduct.getProduct().equals(product)
            ).toList().get(0);
            cartProduct1.setQuantity(quantity);
            return cartProduct1;
        }
        return new CartProduct();
    }

    /**
     * Удаляет указанный продукт из корзины пользователя.
     *
     * @param userId    идентификатор пользователя
     * @param productId идентификатор продукта
     * @throws RuntimeException если продукт не найден в корзине пользователя
     */
    @Override
    public void delProductFromCart(final int userId, final int productId)
            throws RuntimeException {
        User user = userRepositories.findById(userId).orElse(null);
        Product product = productRepositories.findById(productId).orElse(null);
        if (user != null) {
            CartProduct cartProduct1 = cartProductRepositories.findAll().stream().filter(cartProduct ->
                    cartProduct.getCart().equals(user.getCart())
            ).filter(cartProduct ->
                    cartProduct.getProduct().equals(product)
            ).toList().get(0);
            cartProductRepositories.delete(cartProduct1);
        } else {
            throw new RuntimeException("User null");
        }
    }
}
