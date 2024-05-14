package business.hub.cartservice;

import business.hub.cartservice.model.Cart;
import business.hub.cartservice.model.CartProduct;
import business.hub.cartservice.model.Product;
import business.hub.cartservice.model.User;
import business.hub.cartservice.repositories.CartProductRepositories;
import business.hub.cartservice.repositories.CartRepositories;
import business.hub.cartservice.repositories.ProductRepositories;
import business.hub.cartservice.repositories.UserRepositories;
import business.hub.cartservice.service.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CartServiceImpl.class)
class CartServiceApplicationTests {

    @MockBean
    private UserRepositories userRepositories;

    @MockBean
    private ProductRepositories productRepositories;

    @MockBean
    private CartRepositories cartRepositories;

    @MockBean
    private CartProductRepositories cartProductRepositories;

    @Autowired
    private CartServiceImpl cartService;

    private User user;

    private CartProduct cartProduct1;

    private CartProduct cartProduct2;

    private Product product;


    @BeforeEach
    void setUp() {
        user = new User();

        user.setId(1);
        user.setCart(new Cart());
        user.setUsername("Shrek");

        product = new Product();

        product.setId(1);
        product.setDescription("kek");
        product.setManufacturer("shrekich");
        product.setName("productName");
        product.setPrice(111);
        product.setWeight(4);


        cartProduct1 = new CartProduct();
        cartProduct2 = new CartProduct();

        cartProduct1.setId(1);
        cartProduct1.setProduct(product);
        final int quantity1 = 11;
        cartProduct1.setQuantity(quantity1);
        cartProduct1.setCart(user.getCart());

        cartProduct2.setId(2);
        cartProduct2.setProduct(new Product());
        final int quantity2 = 22;
        cartProduct2.setQuantity(quantity2);
        cartProduct2.setCart(user.getCart());

    }

    @Test
    void getProductByIdTest() {

        int userId = 1;
        List<CartProduct> expectedProducts = Arrays.asList(cartProduct1, cartProduct2);


        when(userRepositories.findById(userId)).thenReturn(Optional.of(user));
        when(cartProductRepositories.findAll()).thenReturn(expectedProducts);

        List<CartProduct> actualProducts = cartService.getProductById(userId);

        assertEquals(expectedProducts, actualProducts);
        verify(userRepositories, times(1)).findById(userId);
        verify(cartProductRepositories, times(1)).findAll();
    }

    @Test
    void postUserCartProductTest() {

        cartProduct1.setProduct(product);

        when(productRepositories.save(product)).thenReturn(product);
        when(cartProductRepositories.save(cartProduct1)).thenReturn(cartProduct1);

        CartProduct actualCartProduct = cartService.postUserCartProduct(cartProduct1);

        assertEquals(cartProduct1, actualCartProduct);
        verify(productRepositories, times(1)).save(product);
        verify(cartProductRepositories, times(1)).save(cartProduct1);
    }


    @Test
    void putCartCountTest() {

        int userId = 1;
        int productId = 1;
        final int quantity = 11;

        List<CartProduct> expectedProducts = Arrays.asList(cartProduct1, cartProduct2);

        when(userRepositories.findById(userId)).thenReturn(Optional.of(user));
        when(productRepositories.findById(productId)).thenReturn(Optional.of(product));
        when(cartProductRepositories.findAll()).thenReturn(expectedProducts);

        CartProduct actualCartProduct = cartService.putCartCount(userId, productId, quantity);

        assertEquals(quantity, actualCartProduct.getQuantity());
        verify(userRepositories, times(1)).findById(userId);
        verify(productRepositories, times(1)).findById(productId);
        verify(cartProductRepositories, times(1)).findAll();
    }


    @Test
    void delProductFromCartTest() {

        int userId = 1;
        int productId = 1;


        when(userRepositories.findById(userId)).thenReturn(Optional.of(user));
        when(productRepositories.findById(productId)).thenReturn(Optional.of(product));
        when(cartProductRepositories.findAll()).thenReturn(List.of(cartProduct1));

        cartService.delProductFromCart(userId, productId);

        verify(userRepositories, times(1)).findById(userId);
        verify(productRepositories, times(1)).findById(productId);
        verify(cartProductRepositories, times(1)).delete(cartProduct1);
    }

}
