package business.hub.cartservice.controller;

import business.hub.cartservice.model.CartProduct;
import business.hub.cartservice.repositories.CartProductRepositories;
import business.hub.cartservice.repositories.ProductRepositories;
import business.hub.cartservice.repositories.UserRepositories;
import business.hub.cartservice.service.CartService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST контроллер для работы с корзиной покупателя.
 * При переходе по "/api/cart/{id}" - вернется список продуктов
 * в корзине пользователя с указанным id.
 * При переходе по "/api/cart/{userId}/item/{itemId}" - будет
 * возможность изменить количество продукта в корзине пользователя
 * или удалить продукт из корзины.
 */
@RestController
@RequestMapping("/api/cart")
public class CartControllerRest {

    /**
     * Сервис для работы с корзиной покупателя.
     */
    private final  CartService cartService;
    /**
     * Репозиторий для хранения связей между корзиной и продуктами.
     */
    private final  CartProductRepositories cartProductRepositories;
    /**
     * Репозиторий для хранения информации о пользователях.
     */
    private final  UserRepositories userRepositories;
    /**
     * Репозиторий для хранения информации о продуктах.
     */
    private final  ProductRepositories productRepositories;

    /**
     * Конструктор с инъекцией сервиса, репозиториев и пользователей.
     *
     * @param cartServiceParam - сервис для работы с корзиной покупателя
     * @param cartProductRepositoriesParam - репозиторий для хранения связей между корзиной и продуктами
     * @param userRepositoriesParam - репозиторий для хранения информации о пользователях
     * @param productRepositoriesparam - репозиторий для хранения информации о продуктах
     */
    @Autowired
    public CartControllerRest(final CartService cartServiceParam,
                              final CartProductRepositories cartProductRepositoriesParam,
                              final UserRepositories userRepositoriesParam,
                              final ProductRepositories productRepositoriesparam) {
        this.cartService = cartServiceParam;
        this.cartProductRepositories = cartProductRepositoriesParam;
        this.userRepositories = userRepositoriesParam;
        this.productRepositories = productRepositoriesparam;
    }

    /**
     * Получает содержимое корзины по идентификатору пользователя.
     *
     * @param id - идентификатор пользователя
     * @return список продуктов в корзине пользователя
     */
    @GetMapping("/{id}")
    ResponseEntity<List<CartProduct>> getCart(final @PathVariable int id) {
        return ResponseEntity.ok(cartService.getProductById(id));
    }

    /**
     * Добавляет продукт в корзину пользователя.
     *
     * @param cartProduct - продукт для добавления в корзину
     * @return продукт в корзине пользователя
     */
    @PostMapping("/")
    ResponseEntity<CartProduct> postCart(final @RequestBody CartProduct cartProduct) {
        return ResponseEntity.ok(cartService.postUserCartProduct(cartProduct));
    }

    /**
     * Изменяет количество продукта в корзине пользователя.
     *
     * @param userId - идентификатор пользователя
     * @param itemId - идентификатор продукта
     * @param quantity - новое количество продукта
     * @return продукт в корзине пользователя с обновленным количеством
     */
    @PutMapping("/{userId}/item/{itemId}")
    ResponseEntity<CartProduct> editCount(final @PathVariable("userId") int userId,
                                          final @PathVariable("itemId") int itemId,
                                          final @RequestParam("quantity") int quantity) {
        return ResponseEntity.ok(cartService.putCartCount(userId, itemId, quantity));
    }

    /**
     * Удаляет продукт из корзины пользователя.
     *
     * @param userId - идентификатор пользователя
     * @param itemId - идентификатор продукта
     * @return статус ответа: OK, если продукт успешно удален, CONFLICT,
     * если продукт не найден в корзине
     */
    @DeleteMapping("/{userId}/item/{itemId}")
    ResponseEntity<HttpStatus> delCartProduct(
            final @PathVariable("userId") int userId,
            final @PathVariable("itemId") int itemId
    ) {
        try {
            cartService.delProductFromCart(userId, itemId);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(HttpStatus.CONFLICT);
        }
    }
}
